import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IHorario } from 'app/shared/model/horario.model';

type EntityResponseType = HttpResponse<IHorario>;
type EntityArrayResponseType = HttpResponse<IHorario[]>;

@Injectable({ providedIn: 'root' })
export class HorarioService {
  public resourceUrl = SERVER_API_URL + 'api/horarios';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/horarios';

  constructor(protected http: HttpClient) {}

  create(horario: IHorario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horario);
    return this.http
      .post<IHorario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(horario: IHorario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horario);
    return this.http
      .put<IHorario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHorario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHorario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHorario[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(horario: IHorario): IHorario {
    const copy: IHorario = Object.assign({}, horario, {
      inicioAula: horario.inicioAula && horario.inicioAula.isValid() ? horario.inicioAula.toJSON() : undefined,
      terminoAlua: horario.terminoAlua && horario.terminoAlua.isValid() ? horario.terminoAlua.toJSON() : undefined,
      intervalo: horario.intervalo && horario.intervalo.isValid() ? horario.intervalo.toJSON() : undefined,
      data: horario.data && horario.data.isValid() ? horario.data.toJSON() : undefined,
      anoLectivo: horario.anoLectivo && horario.anoLectivo.isValid() ? horario.anoLectivo.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicioAula = res.body.inicioAula ? moment(res.body.inicioAula) : undefined;
      res.body.terminoAlua = res.body.terminoAlua ? moment(res.body.terminoAlua) : undefined;
      res.body.intervalo = res.body.intervalo ? moment(res.body.intervalo) : undefined;
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
      res.body.anoLectivo = res.body.anoLectivo ? moment(res.body.anoLectivo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((horario: IHorario) => {
        horario.inicioAula = horario.inicioAula ? moment(horario.inicioAula) : undefined;
        horario.terminoAlua = horario.terminoAlua ? moment(horario.terminoAlua) : undefined;
        horario.intervalo = horario.intervalo ? moment(horario.intervalo) : undefined;
        horario.data = horario.data ? moment(horario.data) : undefined;
        horario.anoLectivo = horario.anoLectivo ? moment(horario.anoLectivo) : undefined;
      });
    }
    return res;
  }
}
