import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPlanoActividade } from 'app/shared/model/plano-actividade.model';

type EntityResponseType = HttpResponse<IPlanoActividade>;
type EntityArrayResponseType = HttpResponse<IPlanoActividade[]>;

@Injectable({ providedIn: 'root' })
export class PlanoActividadeService {
  public resourceUrl = SERVER_API_URL + 'api/plano-actividades';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-actividades';

  constructor(protected http: HttpClient) {}

  create(planoActividade: IPlanoActividade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoActividade);
    return this.http
      .post<IPlanoActividade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoActividade: IPlanoActividade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoActividade);
    return this.http
      .put<IPlanoActividade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoActividade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoActividade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoActividade[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(planoActividade: IPlanoActividade): IPlanoActividade {
    const copy: IPlanoActividade = Object.assign({}, planoActividade, {
      de: planoActividade.de && planoActividade.de.isValid() ? planoActividade.de.format(DATE_FORMAT) : undefined,
      ate: planoActividade.ate && planoActividade.ate.isValid() ? planoActividade.ate.format(DATE_FORMAT) : undefined,
      anoLectivo:
        planoActividade.anoLectivo && planoActividade.anoLectivo.isValid() ? planoActividade.anoLectivo.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.de = res.body.de ? moment(res.body.de) : undefined;
      res.body.ate = res.body.ate ? moment(res.body.ate) : undefined;
      res.body.anoLectivo = res.body.anoLectivo ? moment(res.body.anoLectivo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((planoActividade: IPlanoActividade) => {
        planoActividade.de = planoActividade.de ? moment(planoActividade.de) : undefined;
        planoActividade.ate = planoActividade.ate ? moment(planoActividade.ate) : undefined;
        planoActividade.anoLectivo = planoActividade.anoLectivo ? moment(planoActividade.anoLectivo) : undefined;
      });
    }
    return res;
  }
}
