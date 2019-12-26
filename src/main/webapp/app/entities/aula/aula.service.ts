import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAula } from 'app/shared/model/aula.model';

type EntityResponseType = HttpResponse<IAula>;
type EntityArrayResponseType = HttpResponse<IAula[]>;

@Injectable({ providedIn: 'root' })
export class AulaService {
  public resourceUrl = SERVER_API_URL + 'api/aulas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aulas';

  constructor(protected http: HttpClient) {}

  create(aula: IAula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aula);
    return this.http
      .post<IAula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aula: IAula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aula);
    return this.http
      .put<IAula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAula[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(aula: IAula): IAula {
    const copy: IAula = Object.assign({}, aula, {
      data: aula.data && aula.data.isValid() ? aula.data.toJSON() : undefined
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
      res.body.forEach((aula: IAula) => {
        aula.data = aula.data ? moment(aula.data) : undefined;
      });
    }
    return res;
  }
}
