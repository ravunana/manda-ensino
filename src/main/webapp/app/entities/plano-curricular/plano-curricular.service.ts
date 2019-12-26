import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';

type EntityResponseType = HttpResponse<IPlanoCurricular>;
type EntityArrayResponseType = HttpResponse<IPlanoCurricular[]>;

@Injectable({ providedIn: 'root' })
export class PlanoCurricularService {
  public resourceUrl = SERVER_API_URL + 'api/plano-curriculars';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/plano-curriculars';

  constructor(protected http: HttpClient) {}

  create(planoCurricular: IPlanoCurricular): Observable<EntityResponseType> {
    return this.http.post<IPlanoCurricular>(this.resourceUrl, planoCurricular, { observe: 'response' });
  }

  update(planoCurricular: IPlanoCurricular): Observable<EntityResponseType> {
    return this.http.put<IPlanoCurricular>(this.resourceUrl, planoCurricular, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoCurricular[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
