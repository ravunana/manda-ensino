import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAssinaturaDigital } from 'app/shared/model/assinatura-digital.model';

type EntityResponseType = HttpResponse<IAssinaturaDigital>;
type EntityArrayResponseType = HttpResponse<IAssinaturaDigital[]>;

@Injectable({ providedIn: 'root' })
export class AssinaturaDigitalService {
  public resourceUrl = SERVER_API_URL + 'api/assinatura-digitals';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/assinatura-digitals';

  constructor(protected http: HttpClient) {}

  create(assinaturaDigital: IAssinaturaDigital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assinaturaDigital);
    return this.http
      .post<IAssinaturaDigital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assinaturaDigital: IAssinaturaDigital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assinaturaDigital);
    return this.http
      .put<IAssinaturaDigital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssinaturaDigital>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssinaturaDigital[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssinaturaDigital[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(assinaturaDigital: IAssinaturaDigital): IAssinaturaDigital {
    const copy: IAssinaturaDigital = Object.assign({}, assinaturaDigital, {
      data: assinaturaDigital.data && assinaturaDigital.data.isValid() ? assinaturaDigital.data.toJSON() : undefined
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
      res.body.forEach((assinaturaDigital: IAssinaturaDigital) => {
        assinaturaDigital.data = assinaturaDigital.data ? moment(assinaturaDigital.data) : undefined;
      });
    }
    return res;
  }
}
