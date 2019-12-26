import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';

type EntityResponseType = HttpResponse<ICategoriaRequerimento>;
type EntityArrayResponseType = HttpResponse<ICategoriaRequerimento[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaRequerimentoService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-requerimentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/categoria-requerimentos';

  constructor(protected http: HttpClient) {}

  create(categoriaRequerimento: ICategoriaRequerimento): Observable<EntityResponseType> {
    return this.http.post<ICategoriaRequerimento>(this.resourceUrl, categoriaRequerimento, { observe: 'response' });
  }

  update(categoriaRequerimento: ICategoriaRequerimento): Observable<EntityResponseType> {
    return this.http.put<ICategoriaRequerimento>(this.resourceUrl, categoriaRequerimento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaRequerimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaRequerimento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaRequerimento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
