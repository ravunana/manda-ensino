import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

type EntityResponseType = HttpResponse<IContactoPessoa>;
type EntityArrayResponseType = HttpResponse<IContactoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class ContactoPessoaService {
  public resourceUrl = SERVER_API_URL + 'api/contacto-pessoas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/contacto-pessoas';

  constructor(protected http: HttpClient) {}

  create(contactoPessoa: IContactoPessoa): Observable<EntityResponseType> {
    return this.http.post<IContactoPessoa>(this.resourceUrl, contactoPessoa, { observe: 'response' });
  }

  update(contactoPessoa: IContactoPessoa): Observable<EntityResponseType> {
    return this.http.put<IContactoPessoa>(this.resourceUrl, contactoPessoa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoPessoa[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
