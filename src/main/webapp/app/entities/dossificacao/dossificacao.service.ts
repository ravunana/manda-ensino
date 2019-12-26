import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDossificacao } from 'app/shared/model/dossificacao.model';

type EntityResponseType = HttpResponse<IDossificacao>;
type EntityArrayResponseType = HttpResponse<IDossificacao[]>;

@Injectable({ providedIn: 'root' })
export class DossificacaoService {
  public resourceUrl = SERVER_API_URL + 'api/dossificacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/dossificacaos';

  constructor(protected http: HttpClient) {}

  create(dossificacao: IDossificacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dossificacao);
    return this.http
      .post<IDossificacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dossificacao: IDossificacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dossificacao);
    return this.http
      .put<IDossificacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDossificacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDossificacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDossificacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dossificacao: IDossificacao): IDossificacao {
    const copy: IDossificacao = Object.assign({}, dossificacao, {
      anoLectivo: dossificacao.anoLectivo && dossificacao.anoLectivo.isValid() ? dossificacao.anoLectivo.format(DATE_FORMAT) : undefined,
      de: dossificacao.de && dossificacao.de.isValid() ? dossificacao.de.format(DATE_FORMAT) : undefined,
      ate: dossificacao.ate && dossificacao.ate.isValid() ? dossificacao.ate.format(DATE_FORMAT) : undefined,
      tempoAula: dossificacao.tempoAula && dossificacao.tempoAula.isValid() ? dossificacao.tempoAula.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.anoLectivo = res.body.anoLectivo ? moment(res.body.anoLectivo) : undefined;
      res.body.de = res.body.de ? moment(res.body.de) : undefined;
      res.body.ate = res.body.ate ? moment(res.body.ate) : undefined;
      res.body.tempoAula = res.body.tempoAula ? moment(res.body.tempoAula) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dossificacao: IDossificacao) => {
        dossificacao.anoLectivo = dossificacao.anoLectivo ? moment(dossificacao.anoLectivo) : undefined;
        dossificacao.de = dossificacao.de ? moment(dossificacao.de) : undefined;
        dossificacao.ate = dossificacao.ate ? moment(dossificacao.ate) : undefined;
        dossificacao.tempoAula = dossificacao.tempoAula ? moment(dossificacao.tempoAula) : undefined;
      });
    }
    return res;
  }
}
