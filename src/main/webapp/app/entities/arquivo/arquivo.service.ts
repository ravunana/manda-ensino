import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IArquivo } from 'app/shared/model/arquivo.model';

type EntityResponseType = HttpResponse<IArquivo>;
type EntityArrayResponseType = HttpResponse<IArquivo[]>;

@Injectable({ providedIn: 'root' })
export class ArquivoService {
  public resourceUrl = SERVER_API_URL + 'api/arquivos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/arquivos';

  constructor(protected http: HttpClient) {}

  create(arquivo: IArquivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arquivo);
    return this.http
      .post<IArquivo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arquivo: IArquivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arquivo);
    return this.http
      .put<IArquivo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArquivo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArquivo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArquivo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(arquivo: IArquivo): IArquivo {
    const copy: IArquivo = Object.assign({}, arquivo, {
      data: arquivo.data && arquivo.data.isValid() ? arquivo.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((arquivo: IArquivo) => {
        arquivo.data = arquivo.data ? moment(arquivo.data) : undefined;
      });
    }
    return res;
  }
}
