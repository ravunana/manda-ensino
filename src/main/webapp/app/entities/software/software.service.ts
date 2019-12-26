import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ISoftware } from 'app/shared/model/software.model';

type EntityResponseType = HttpResponse<ISoftware>;
type EntityArrayResponseType = HttpResponse<ISoftware[]>;

@Injectable({ providedIn: 'root' })
export class SoftwareService {
  public resourceUrl = SERVER_API_URL + 'api/softwares';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/softwares';

  constructor(protected http: HttpClient) {}

  create(software: ISoftware): Observable<EntityResponseType> {
    return this.http.post<ISoftware>(this.resourceUrl, software, { observe: 'response' });
  }

  update(software: ISoftware): Observable<EntityResponseType> {
    return this.http.put<ISoftware>(this.resourceUrl, software, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISoftware>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISoftware[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISoftware[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
