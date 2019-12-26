import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAluno } from 'app/shared/model/aluno.model';

type EntityResponseType = HttpResponse<IAluno>;
type EntityArrayResponseType = HttpResponse<IAluno[]>;

@Injectable({ providedIn: 'root' })
export class AlunoService {
  public resourceUrl = SERVER_API_URL + 'api/alunos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/alunos';

  constructor(protected http: HttpClient) {}

  create(aluno: IAluno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aluno);
    return this.http
      .post<IAluno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aluno: IAluno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aluno);
    return this.http
      .put<IAluno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAluno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAluno[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAluno[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(aluno: IAluno): IAluno {
    const copy: IAluno = Object.assign({}, aluno, {
      data: aluno.data && aluno.data.isValid() ? aluno.data.toJSON() : undefined
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
      res.body.forEach((aluno: IAluno) => {
        aluno.data = aluno.data ? moment(aluno.data) : undefined;
      });
    }
    return res;
  }
}
