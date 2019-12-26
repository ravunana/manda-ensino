import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICurso } from 'app/shared/model/curso.model';

type EntityResponseType = HttpResponse<ICurso>;
type EntityArrayResponseType = HttpResponse<ICurso[]>;

@Injectable({ providedIn: 'root' })
export class CursoService {
  public resourceUrl = SERVER_API_URL + 'api/cursos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cursos';

  constructor(protected http: HttpClient) {}

  create(curso: ICurso): Observable<EntityResponseType> {
    return this.http.post<ICurso>(this.resourceUrl, curso, { observe: 'response' });
  }

  update(curso: ICurso): Observable<EntityResponseType> {
    return this.http.put<ICurso>(this.resourceUrl, curso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurso[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
