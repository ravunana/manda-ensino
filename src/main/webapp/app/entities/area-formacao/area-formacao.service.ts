import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAreaFormacao } from 'app/shared/model/area-formacao.model';

type EntityResponseType = HttpResponse<IAreaFormacao>;
type EntityArrayResponseType = HttpResponse<IAreaFormacao[]>;

@Injectable({ providedIn: 'root' })
export class AreaFormacaoService {
  public resourceUrl = SERVER_API_URL + 'api/area-formacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/area-formacaos';

  constructor(protected http: HttpClient) {}

  create(areaFormacao: IAreaFormacao): Observable<EntityResponseType> {
    return this.http.post<IAreaFormacao>(this.resourceUrl, areaFormacao, { observe: 'response' });
  }

  update(areaFormacao: IAreaFormacao): Observable<EntityResponseType> {
    return this.http.put<IAreaFormacao>(this.resourceUrl, areaFormacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaFormacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaFormacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaFormacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
