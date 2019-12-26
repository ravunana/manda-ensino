import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IEncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';

type EntityResponseType = HttpResponse<IEncarregadoEducacao>;
type EntityArrayResponseType = HttpResponse<IEncarregadoEducacao[]>;

@Injectable({ providedIn: 'root' })
export class EncarregadoEducacaoService {
  public resourceUrl = SERVER_API_URL + 'api/encarregado-educacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/encarregado-educacaos';

  constructor(protected http: HttpClient) {}

  create(encarregadoEducacao: IEncarregadoEducacao): Observable<EntityResponseType> {
    return this.http.post<IEncarregadoEducacao>(this.resourceUrl, encarregadoEducacao, { observe: 'response' });
  }

  update(encarregadoEducacao: IEncarregadoEducacao): Observable<EntityResponseType> {
    return this.http.put<IEncarregadoEducacao>(this.resourceUrl, encarregadoEducacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEncarregadoEducacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEncarregadoEducacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEncarregadoEducacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
