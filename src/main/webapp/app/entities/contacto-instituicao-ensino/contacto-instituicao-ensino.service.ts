import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

type EntityResponseType = HttpResponse<IContactoInstituicaoEnsino>;
type EntityArrayResponseType = HttpResponse<IContactoInstituicaoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class ContactoInstituicaoEnsinoService {
  public resourceUrl = SERVER_API_URL + 'api/contacto-instituicao-ensinos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/contacto-instituicao-ensinos';

  constructor(protected http: HttpClient) {}

  create(contactoInstituicaoEnsino: IContactoInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.post<IContactoInstituicaoEnsino>(this.resourceUrl, contactoInstituicaoEnsino, { observe: 'response' });
  }

  update(contactoInstituicaoEnsino: IContactoInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.put<IContactoInstituicaoEnsino>(this.resourceUrl, contactoInstituicaoEnsino, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactoInstituicaoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoInstituicaoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactoInstituicaoEnsino[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
