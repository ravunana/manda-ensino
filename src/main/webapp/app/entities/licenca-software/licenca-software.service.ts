import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

type EntityResponseType = HttpResponse<ILicencaSoftware>;
type EntityArrayResponseType = HttpResponse<ILicencaSoftware[]>;

@Injectable({ providedIn: 'root' })
export class LicencaSoftwareService {
  public resourceUrl = SERVER_API_URL + 'api/licenca-softwares';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/licenca-softwares';

  constructor(protected http: HttpClient) {}

  create(licencaSoftware: ILicencaSoftware): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licencaSoftware);
    return this.http
      .post<ILicencaSoftware>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(licencaSoftware: ILicencaSoftware): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licencaSoftware);
    return this.http
      .put<ILicencaSoftware>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILicencaSoftware>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicencaSoftware[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicencaSoftware[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(licencaSoftware: ILicencaSoftware): ILicencaSoftware {
    const copy: ILicencaSoftware = Object.assign({}, licencaSoftware, {
      inicio: licencaSoftware.inicio && licencaSoftware.inicio.isValid() ? licencaSoftware.inicio.toJSON() : undefined,
      fim: licencaSoftware.fim && licencaSoftware.fim.isValid() ? licencaSoftware.fim.toJSON() : undefined,
      data: licencaSoftware.data && licencaSoftware.data.isValid() ? licencaSoftware.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio ? moment(res.body.inicio) : undefined;
      res.body.fim = res.body.fim ? moment(res.body.fim) : undefined;
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((licencaSoftware: ILicencaSoftware) => {
        licencaSoftware.inicio = licencaSoftware.inicio ? moment(licencaSoftware.inicio) : undefined;
        licencaSoftware.fim = licencaSoftware.fim ? moment(licencaSoftware.fim) : undefined;
        licencaSoftware.data = licencaSoftware.data ? moment(licencaSoftware.data) : undefined;
      });
    }
    return res;
  }
}
