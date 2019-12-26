import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMatrizCurricular } from 'app/shared/model/matriz-curricular.model';

type EntityResponseType = HttpResponse<IMatrizCurricular>;
type EntityArrayResponseType = HttpResponse<IMatrizCurricular[]>;

@Injectable({ providedIn: 'root' })
export class MatrizCurricularService {
  public resourceUrl = SERVER_API_URL + 'api/matriz-curriculars';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/matriz-curriculars';

  constructor(protected http: HttpClient) {}

  create(matrizCurricular: IMatrizCurricular): Observable<EntityResponseType> {
    return this.http.post<IMatrizCurricular>(this.resourceUrl, matrizCurricular, { observe: 'response' });
  }

  update(matrizCurricular: IMatrizCurricular): Observable<EntityResponseType> {
    return this.http.put<IMatrizCurricular>(this.resourceUrl, matrizCurricular, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatrizCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatrizCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatrizCurricular[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
