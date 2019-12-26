import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ISerieDocumento } from 'app/shared/model/serie-documento.model';

type EntityResponseType = HttpResponse<ISerieDocumento>;
type EntityArrayResponseType = HttpResponse<ISerieDocumento[]>;

@Injectable({ providedIn: 'root' })
export class SerieDocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/serie-documentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/serie-documentos';

  constructor(protected http: HttpClient) {}

  create(serieDocumento: ISerieDocumento): Observable<EntityResponseType> {
    return this.http.post<ISerieDocumento>(this.resourceUrl, serieDocumento, { observe: 'response' });
  }

  update(serieDocumento: ISerieDocumento): Observable<EntityResponseType> {
    return this.http.put<ISerieDocumento>(this.resourceUrl, serieDocumento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISerieDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISerieDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISerieDocumento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
