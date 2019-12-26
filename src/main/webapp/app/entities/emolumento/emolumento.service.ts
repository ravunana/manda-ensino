import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IEmolumento } from 'app/shared/model/emolumento.model';

type EntityResponseType = HttpResponse<IEmolumento>;
type EntityArrayResponseType = HttpResponse<IEmolumento[]>;

@Injectable({ providedIn: 'root' })
export class EmolumentoService {
  public resourceUrl = SERVER_API_URL + 'api/emolumentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/emolumentos';

  constructor(protected http: HttpClient) {}

  create(emolumento: IEmolumento): Observable<EntityResponseType> {
    return this.http.post<IEmolumento>(this.resourceUrl, emolumento, { observe: 'response' });
  }

  update(emolumento: IEmolumento): Observable<EntityResponseType> {
    return this.http.put<IEmolumento>(this.resourceUrl, emolumento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmolumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmolumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmolumento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
