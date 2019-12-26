import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';

type EntityResponseType = HttpResponse<ICategoriaValiacao>;
type EntityArrayResponseType = HttpResponse<ICategoriaValiacao[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaValiacaoService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-valiacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/categoria-valiacaos';

  constructor(protected http: HttpClient) {}

  create(categoriaValiacao: ICategoriaValiacao): Observable<EntityResponseType> {
    return this.http.post<ICategoriaValiacao>(this.resourceUrl, categoriaValiacao, { observe: 'response' });
  }

  update(categoriaValiacao: ICategoriaValiacao): Observable<EntityResponseType> {
    return this.http.put<ICategoriaValiacao>(this.resourceUrl, categoriaValiacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaValiacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaValiacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaValiacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
