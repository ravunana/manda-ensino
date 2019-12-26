import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDeposito } from 'app/shared/model/deposito.model';

type EntityResponseType = HttpResponse<IDeposito>;
type EntityArrayResponseType = HttpResponse<IDeposito[]>;

@Injectable({ providedIn: 'root' })
export class DepositoService {
  public resourceUrl = SERVER_API_URL + 'api/depositos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/depositos';

  constructor(protected http: HttpClient) {}

  create(deposito: IDeposito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deposito);
    return this.http
      .post<IDeposito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deposito: IDeposito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deposito);
    return this.http
      .put<IDeposito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeposito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeposito[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeposito[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(deposito: IDeposito): IDeposito {
    const copy: IDeposito = Object.assign({}, deposito, {
      dataDeposito: deposito.dataDeposito && deposito.dataDeposito.isValid() ? deposito.dataDeposito.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDeposito = res.body.dataDeposito ? moment(res.body.dataDeposito) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((deposito: IDeposito) => {
        deposito.dataDeposito = deposito.dataDeposito ? moment(deposito.dataDeposito) : undefined;
      });
    }
    return res;
  }
}
