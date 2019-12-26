import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

type EntityResponseType = HttpResponse<ICriterioAvaliacao>;
type EntityArrayResponseType = HttpResponse<ICriterioAvaliacao[]>;

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoService {
  public resourceUrl = SERVER_API_URL + 'api/criterio-avaliacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/criterio-avaliacaos';

  constructor(protected http: HttpClient) {}

  create(criterioAvaliacao: ICriterioAvaliacao): Observable<EntityResponseType> {
    return this.http.post<ICriterioAvaliacao>(this.resourceUrl, criterioAvaliacao, { observe: 'response' });
  }

  update(criterioAvaliacao: ICriterioAvaliacao): Observable<EntityResponseType> {
    return this.http.put<ICriterioAvaliacao>(this.resourceUrl, criterioAvaliacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriterioAvaliacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriterioAvaliacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriterioAvaliacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
