import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IFichaMedica } from 'app/shared/model/ficha-medica.model';

type EntityResponseType = HttpResponse<IFichaMedica>;
type EntityArrayResponseType = HttpResponse<IFichaMedica[]>;

@Injectable({ providedIn: 'root' })
export class FichaMedicaService {
  public resourceUrl = SERVER_API_URL + 'api/ficha-medicas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ficha-medicas';

  constructor(protected http: HttpClient) {}

  create(fichaMedica: IFichaMedica): Observable<EntityResponseType> {
    return this.http.post<IFichaMedica>(this.resourceUrl, fichaMedica, { observe: 'response' });
  }

  update(fichaMedica: IFichaMedica): Observable<EntityResponseType> {
    return this.http.put<IFichaMedica>(this.resourceUrl, fichaMedica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFichaMedica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaMedica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaMedica[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
