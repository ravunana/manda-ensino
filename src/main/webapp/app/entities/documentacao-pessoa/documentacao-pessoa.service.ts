import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

type EntityResponseType = HttpResponse<IDocumentacaoPessoa>;
type EntityArrayResponseType = HttpResponse<IDocumentacaoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class DocumentacaoPessoaService {
  public resourceUrl = SERVER_API_URL + 'api/documentacao-pessoas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/documentacao-pessoas';

  constructor(protected http: HttpClient) {}

  create(documentacaoPessoa: IDocumentacaoPessoa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentacaoPessoa);
    return this.http
      .post<IDocumentacaoPessoa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documentacaoPessoa: IDocumentacaoPessoa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentacaoPessoa);
    return this.http
      .put<IDocumentacaoPessoa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumentacaoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentacaoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumentacaoPessoa[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(documentacaoPessoa: IDocumentacaoPessoa): IDocumentacaoPessoa {
    const copy: IDocumentacaoPessoa = Object.assign({}, documentacaoPessoa, {
      emissao:
        documentacaoPessoa.emissao && documentacaoPessoa.emissao.isValid() ? documentacaoPessoa.emissao.format(DATE_FORMAT) : undefined,
      validade:
        documentacaoPessoa.validade && documentacaoPessoa.validade.isValid() ? documentacaoPessoa.validade.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.emissao = res.body.emissao ? moment(res.body.emissao) : undefined;
      res.body.validade = res.body.validade ? moment(res.body.validade) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((documentacaoPessoa: IDocumentacaoPessoa) => {
        documentacaoPessoa.emissao = documentacaoPessoa.emissao ? moment(documentacaoPessoa.emissao) : undefined;
        documentacaoPessoa.validade = documentacaoPessoa.validade ? moment(documentacaoPessoa.validade) : undefined;
      });
    }
    return res;
  }
}
