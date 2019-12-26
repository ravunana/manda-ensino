import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IRelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

type EntityResponseType = HttpResponse<IRelacionamentoPessoa>;
type EntityArrayResponseType = HttpResponse<IRelacionamentoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class RelacionamentoPessoaService {
  public resourceUrl = SERVER_API_URL + 'api/relacionamento-pessoas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/relacionamento-pessoas';

  constructor(protected http: HttpClient) {}

  create(relacionamentoPessoa: IRelacionamentoPessoa): Observable<EntityResponseType> {
    return this.http.post<IRelacionamentoPessoa>(this.resourceUrl, relacionamentoPessoa, { observe: 'response' });
  }

  update(relacionamentoPessoa: IRelacionamentoPessoa): Observable<EntityResponseType> {
    return this.http.put<IRelacionamentoPessoa>(this.resourceUrl, relacionamentoPessoa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRelacionamentoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelacionamentoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelacionamentoPessoa[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
