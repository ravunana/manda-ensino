import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

type EntityResponseType = HttpResponse<ILocalizacaoInstituicaoEnsino>;
type EntityArrayResponseType = HttpResponse<ILocalizacaoInstituicaoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class LocalizacaoInstituicaoEnsinoService {
  public resourceUrl = SERVER_API_URL + 'api/localizacao-instituicao-ensinos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/localizacao-instituicao-ensinos';

  constructor(protected http: HttpClient) {}

  create(localizacaoInstituicaoEnsino: ILocalizacaoInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.post<ILocalizacaoInstituicaoEnsino>(this.resourceUrl, localizacaoInstituicaoEnsino, { observe: 'response' });
  }

  update(localizacaoInstituicaoEnsino: ILocalizacaoInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.put<ILocalizacaoInstituicaoEnsino>(this.resourceUrl, localizacaoInstituicaoEnsino, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocalizacaoInstituicaoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalizacaoInstituicaoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalizacaoInstituicaoEnsino[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
