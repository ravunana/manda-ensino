import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

type EntityResponseType = HttpResponse<IInstituicaoEnsino>;
type EntityArrayResponseType = HttpResponse<IInstituicaoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoService {
  public resourceUrl = SERVER_API_URL + 'api/instituicao-ensinos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/instituicao-ensinos';

  constructor(protected http: HttpClient) {}

  create(instituicaoEnsino: IInstituicaoEnsino): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instituicaoEnsino);
    return this.http
      .post<IInstituicaoEnsino>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(instituicaoEnsino: IInstituicaoEnsino): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instituicaoEnsino);
    return this.http
      .put<IInstituicaoEnsino>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInstituicaoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInstituicaoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInstituicaoEnsino[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(instituicaoEnsino: IInstituicaoEnsino): IInstituicaoEnsino {
    const copy: IInstituicaoEnsino = Object.assign({}, instituicaoEnsino, {
      fundacao:
        instituicaoEnsino.fundacao && instituicaoEnsino.fundacao.isValid() ? instituicaoEnsino.fundacao.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fundacao = res.body.fundacao ? moment(res.body.fundacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((instituicaoEnsino: IInstituicaoEnsino) => {
        instituicaoEnsino.fundacao = instituicaoEnsino.fundacao ? moment(instituicaoEnsino.fundacao) : undefined;
      });
    }
    return res;
  }
}
