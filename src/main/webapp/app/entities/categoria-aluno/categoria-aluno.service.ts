import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICategoriaAluno } from 'app/shared/model/categoria-aluno.model';

type EntityResponseType = HttpResponse<ICategoriaAluno>;
type EntityArrayResponseType = HttpResponse<ICategoriaAluno[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaAlunoService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-alunos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/categoria-alunos';

  constructor(protected http: HttpClient) {}

  create(categoriaAluno: ICategoriaAluno): Observable<EntityResponseType> {
    return this.http.post<ICategoriaAluno>(this.resourceUrl, categoriaAluno, { observe: 'response' });
  }

  update(categoriaAluno: ICategoriaAluno): Observable<EntityResponseType> {
    return this.http.put<ICategoriaAluno>(this.resourceUrl, categoriaAluno, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaAluno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaAluno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaAluno[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
