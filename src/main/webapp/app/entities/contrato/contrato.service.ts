import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IContrato } from 'app/shared/model/contrato.model';

type EntityResponseType = HttpResponse<IContrato>;
type EntityArrayResponseType = HttpResponse<IContrato[]>;

@Injectable({ providedIn: 'root' })
export class ContratoService {
  public resourceUrl = SERVER_API_URL + 'api/contratoes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/contratoes';

  constructor(protected http: HttpClient) {}

  create(contrato: IContrato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrato);
    return this.http
      .post<IContrato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contrato: IContrato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrato);
    return this.http
      .put<IContrato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContrato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContrato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContrato[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(contrato: IContrato): IContrato {
    const copy: IContrato = Object.assign({}, contrato, {
      de: contrato.de && contrato.de.isValid() ? contrato.de.format(DATE_FORMAT) : undefined,
      ate: contrato.ate && contrato.ate.isValid() ? contrato.ate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.de = res.body.de ? moment(res.body.de) : undefined;
      res.body.ate = res.body.ate ? moment(res.body.ate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contrato: IContrato) => {
        contrato.de = contrato.de ? moment(contrato.de) : undefined;
        contrato.ate = contrato.ate ? moment(contrato.ate) : undefined;
      });
    }
    return res;
  }
}
