import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IRequerimento } from 'app/shared/model/requerimento.model';

type EntityResponseType = HttpResponse<IRequerimento>;
type EntityArrayResponseType = HttpResponse<IRequerimento[]>;

@Injectable({ providedIn: 'root' })
export class RequerimentoService {
  public resourceUrl = SERVER_API_URL + 'api/requerimentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/requerimentos';

  constructor(protected http: HttpClient) {}

  create(requerimento: IRequerimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimento);
    return this.http
      .post<IRequerimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requerimento: IRequerimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimento);
    return this.http
      .put<IRequerimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequerimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequerimento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequerimento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(requerimento: IRequerimento): IRequerimento {
    const copy: IRequerimento = Object.assign({}, requerimento, {
      data: requerimento.data && requerimento.data.isValid() ? requerimento.data.toJSON() : undefined
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
      res.body.forEach((requerimento: IRequerimento) => {
        requerimento.data = requerimento.data ? moment(requerimento.data) : undefined;
      });
    }
    return res;
  }
}
