import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DetalheOcorrenciaService } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia.service';
import { IDetalheOcorrencia, DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

describe('Service Tests', () => {
  describe('DetalheOcorrencia Service', () => {
    let injector: TestBed;
    let service: DetalheOcorrenciaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDetalheOcorrencia;
    let expectedResult: IDetalheOcorrencia | IDetalheOcorrencia[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DetalheOcorrenciaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DetalheOcorrencia(0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT)
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

      it('should create a DetalheOcorrencia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate
          },
          returnedFromService
        );
        service
          .create(new DetalheOcorrencia())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DetalheOcorrencia', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            motivo: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate
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

      it('should return a list of DetalheOcorrencia', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            motivo: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate
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

      it('should delete a DetalheOcorrencia', () => {
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
