import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

type EntityResponseType = HttpResponse<IDetalhePagamento>;
type EntityArrayResponseType = HttpResponse<IDetalhePagamento[]>;

@Injectable({ providedIn: 'root' })
export class DetalhePagamentoService {
  public resourceUrl = SERVER_API_URL + 'api/detalhe-pagamentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/detalhe-pagamentos';

  constructor(protected http: HttpClient) {}

  create(detalhePagamento: IDetalhePagamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalhePagamento);
    return this.http
      .post<IDetalhePagamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detalhePagamento: IDetalhePagamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalhePagamento);
    return this.http
      .put<IDetalhePagamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetalhePagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalhePagamento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalhePagamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(detalhePagamento: IDetalhePagamento): IDetalhePagamento {
    const copy: IDetalhePagamento = Object.assign({}, detalhePagamento, {
      data: detalhePagamento.data && detalhePagamento.data.isValid() ? detalhePagamento.data.toJSON() : undefined,
      vencimento:
        detalhePagamento.vencimento && detalhePagamento.vencimento.isValid() ? detalhePagamento.vencimento.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
      res.body.vencimento = res.body.vencimento ? moment(res.body.vencimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((detalhePagamento: IDetalhePagamento) => {
        detalhePagamento.data = detalhePagamento.data ? moment(detalhePagamento.data) : undefined;
        detalhePagamento.vencimento = detalhePagamento.vencimento ? moment(detalhePagamento.vencimento) : undefined;
      });
    }
    return res;
  }
}
