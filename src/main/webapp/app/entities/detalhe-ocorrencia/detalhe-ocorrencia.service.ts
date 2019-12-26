import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

type EntityResponseType = HttpResponse<IDetalheOcorrencia>;
type EntityArrayResponseType = HttpResponse<IDetalheOcorrencia[]>;

@Injectable({ providedIn: 'root' })
export class DetalheOcorrenciaService {
  public resourceUrl = SERVER_API_URL + 'api/detalhe-ocorrencias';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/detalhe-ocorrencias';

  constructor(protected http: HttpClient) {}

  create(detalheOcorrencia: IDetalheOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheOcorrencia);
    return this.http
      .post<IDetalheOcorrencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(detalheOcorrencia: IDetalheOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalheOcorrencia);
    return this.http
      .put<IDetalheOcorrencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDetalheOcorrencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalheOcorrencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDetalheOcorrencia[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(detalheOcorrencia: IDetalheOcorrencia): IDetalheOcorrencia {
    const copy: IDetalheOcorrencia = Object.assign({}, detalheOcorrencia, {
      de: detalheOcorrencia.de && detalheOcorrencia.de.isValid() ? detalheOcorrencia.de.format(DATE_FORMAT) : undefined,
      ate: detalheOcorrencia.ate && detalheOcorrencia.ate.isValid() ? detalheOcorrencia.ate.format(DATE_FORMAT) : undefined
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
      res.body.forEach((detalheOcorrencia: IDetalheOcorrencia) => {
        detalheOcorrencia.de = detalheOcorrencia.de ? moment(detalheOcorrencia.de) : undefined;
        detalheOcorrencia.ate = detalheOcorrencia.ate ? moment(detalheOcorrencia.ate) : undefined;
      });
    }
    return res;
  }
}
