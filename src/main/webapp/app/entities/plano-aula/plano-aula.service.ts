import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';

type EntityResponseType = HttpResponse<IPlanoAula>;
type EntityArrayResponseType = HttpResponse<IPlanoAula[]>;

@Injectable({ providedIn: 'root' })
export class PlanoAulaService {
  public resourceUrl = SERVER_API_URL + 'api/plano-aulas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-aulas';

  constructor(protected http: HttpClient) {}

  create(planoAula: IPlanoAula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoAula);
    return this.http
      .post<IPlanoAula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoAula: IPlanoAula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoAula);
    return this.http
      .put<IPlanoAula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoAula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoAula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoAula[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(planoAula: IPlanoAula): IPlanoAula {
    const copy: IPlanoAula = Object.assign({}, planoAula, {
      tempo: planoAula.tempo && planoAula.tempo.isValid() ? planoAula.tempo.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tempo = res.body.tempo ? moment(res.body.tempo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((planoAula: IPlanoAula) => {
        planoAula.tempo = planoAula.tempo ? moment(planoAula.tempo) : undefined;
      });
    }
    return res;
  }
}
