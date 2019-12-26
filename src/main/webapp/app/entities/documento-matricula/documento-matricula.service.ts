import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDocumentoMatricula } from 'app/shared/model/documento-matricula.model';

type EntityResponseType = HttpResponse<IDocumentoMatricula>;
type EntityArrayResponseType = HttpResponse<IDocumentoMatricula[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoMatriculaService {
  public resourceUrl = SERVER_API_URL + 'api/documento-matriculas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/documento-matriculas';

  constructor(protected http: HttpClient) {}

  create(documentoMatricula: IDocumentoMatricula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentoMatricula);
    return this.http
      .post<IDocumentoMatricula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documentoMatricula: IDocumentoMatricula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentoMatricula);
    return this.http
      .put<IDocumentoMatricula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumentoMatricula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentoMatricula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentoMatricula[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(documentoMatricula: IDocumentoMatricula): IDocumentoMatricula {
    const copy: IDocumentoMatricula = Object.assign({}, documentoMatricula, {
      data: documentoMatricula.data && documentoMatricula.data.isValid() ? documentoMatricula.data.toJSON() : undefined
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
      res.body.forEach((documentoMatricula: IDocumentoMatricula) => {
        documentoMatricula.data = documentoMatricula.data ? moment(documentoMatricula.data) : undefined;
      });
    }
    return res;
  }
}
