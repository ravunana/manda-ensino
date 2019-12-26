import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IOcorrencia } from 'app/shared/model/ocorrencia.model';

type EntityResponseType = HttpResponse<IOcorrencia>;
type EntityArrayResponseType = HttpResponse<IOcorrencia[]>;

@Injectable({ providedIn: 'root' })
export class OcorrenciaService {
  public resourceUrl = SERVER_API_URL + 'api/ocorrencias';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ocorrencias';

  constructor(protected http: HttpClient) {}

  create(ocorrencia: IOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ocorrencia);
    return this.http
      .post<IOcorrencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ocorrencia: IOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ocorrencia);
    return this.http
      .put<IOcorrencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOcorrencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOcorrencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOcorrencia[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(ocorrencia: IOcorrencia): IOcorrencia {
    const copy: IOcorrencia = Object.assign({}, ocorrencia, {
      data: ocorrencia.data && ocorrencia.data.isValid() ? ocorrencia.data.toJSON() : undefined
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
      res.body.forEach((ocorrencia: IOcorrencia) => {
        ocorrencia.data = ocorrencia.data ? moment(ocorrencia.data) : undefined;
      });
    }
    return res;
  }
}
