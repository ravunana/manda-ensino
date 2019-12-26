import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';

type EntityResponseType = HttpResponse<IUnidadeCurricular>;
type EntityArrayResponseType = HttpResponse<IUnidadeCurricular[]>;

@Injectable({ providedIn: 'root' })
export class UnidadeCurricularService {
  public resourceUrl = SERVER_API_URL + 'api/unidade-curriculars';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/unidade-curriculars';

  constructor(protected http: HttpClient) {}

  create(unidadeCurricular: IUnidadeCurricular): Observable<EntityResponseType> {
    return this.http.post<IUnidadeCurricular>(this.resourceUrl, unidadeCurricular, { observe: 'response' });
  }

  update(unidadeCurricular: IUnidadeCurricular): Observable<EntityResponseType> {
    return this.http.put<IUnidadeCurricular>(this.resourceUrl, unidadeCurricular, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnidadeCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnidadeCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnidadeCurricular[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
