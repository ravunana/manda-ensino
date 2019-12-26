import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { HorarioService } from 'app/entities/horario/horario.service';
import { IHorario, Horario } from 'app/shared/model/horario.model';

describe('Service Tests', () => {
  describe('Horario Service', () => {
    let injector: TestBed;
    let service: HorarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IHorario;
    let expectedResult: IHorario | IHorario[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HorarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Horario(0, currentDate, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicioAula: currentDate.format(DATE_TIME_FORMAT),
            terminoAlua: currentDate.format(DATE_TIME_FORMAT),
            intervalo: currentDate.format(DATE_TIME_FORMAT),
            data: currentDate.format(DATE_TIME_FORMAT),
            anoLectivo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Horario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicioAula: currentDate.format(DATE_TIME_FORMAT),
            terminoAlua: currentDate.format(DATE_TIME_FORMAT),
            intervalo: currentDate.format(DATE_TIME_FORMAT),
            data: currentDate.format(DATE_TIME_FORMAT),
            anoLectivo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicioAula: currentDate,
            terminoAlua: currentDate,
            intervalo: currentDate,
            data: currentDate,
            anoLectivo: currentDate
          },
          returnedFromService
        );
        service
          .create(new Horario())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Horario', () => {
        const returnedFromService = Object.assign(
          {
            inicioAula: currentDate.format(DATE_TIME_FORMAT),
            terminoAlua: currentDate.format(DATE_TIME_FORMAT),
            intervalo: currentDate.format(DATE_TIME_FORMAT),
            diaSemana: 'BBBBBB',
            regimeCurricular: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            anoLectivo: currentDate.format(DATE_FORMAT),
            categoria: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicioAula: currentDate,
            terminoAlua: currentDate,
            intervalo: currentDate,
            data: currentDate,
            anoLectivo: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Horario', () => {
        const returnedFromService = Object.assign(
          {
            inicioAula: currentDate.format(DATE_TIME_FORMAT),
            terminoAlua: currentDate.format(DATE_TIME_FORMAT),
            intervalo: currentDate.format(DATE_TIME_FORMAT),
            diaSemana: 'BBBBBB',
            regimeCurricular: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            anoLectivo: currentDate.format(DATE_FORMAT),
            categoria: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicioAula: currentDate,
            terminoAlua: currentDate,
            intervalo: currentDate,
            data: currentDate,
            anoLectivo: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Horario', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
