import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ISituacaoAcademica } from 'app/shared/model/situacao-academica.model';

type EntityResponseType = HttpResponse<ISituacaoAcademica>;
type EntityArrayResponseType = HttpResponse<ISituacaoAcademica[]>;

@Injectable({ providedIn: 'root' })
export class SituacaoAcademicaService {
  public resourceUrl = SERVER_API_URL + 'api/situacao-academicas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/situacao-academicas';

  constructor(protected http: HttpClient) {}

  create(situacaoAcademica: ISituacaoAcademica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situacaoAcademica);
    return this.http
      .post<ISituacaoAcademica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(situacaoAcademica: ISituacaoAcademica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situacaoAcademica);
    return this.http
      .put<ISituacaoAcademica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISituacaoAcademica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISituacaoAcademica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISituacaoAcademica[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(situacaoAcademica: ISituacaoAcademica): ISituacaoAcademica {
    const copy: ISituacaoAcademica = Object.assign({}, situacaoAcademica, {
      data: situacaoAcademica.data && situacaoAcademica.data.isValid() ? situacaoAcademica.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((situacaoAcademica: ISituacaoAcademica) => {
        situacaoAcademica.data = situacaoAcademica.data ? moment(situacaoAcademica.data) : undefined;
      });
    }
    return res;
  }
}
