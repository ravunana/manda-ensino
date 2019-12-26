import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILookupItem } from 'app/shared/model/lookup-item.model';

type EntityResponseType = HttpResponse<ILookupItem>;
type EntityArrayResponseType = HttpResponse<ILookupItem[]>;

@Injectable({ providedIn: 'root' })
export class LookupItemService {
  public resourceUrl = SERVER_API_URL + 'api/lookup-items';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/lookup-items';

  constructor(protected http: HttpClient) {}

  create(lookupItem: ILookupItem): Observable<EntityResponseType> {
    return this.http.post<ILookupItem>(this.resourceUrl, lookupItem, { observe: 'response' });
  }

  update(lookupItem: ILookupItem): Observable<EntityResponseType> {
    return this.http.put<ILookupItem>(this.resourceUrl, lookupItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILookupItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILookupItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILookupItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
