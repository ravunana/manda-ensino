import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ContratoService } from 'app/entities/contrato/contrato.service';
import { IContrato, Contrato } from 'app/shared/model/contrato.model';

describe('Service Tests', () => {
  describe('Contrato Service', () => {
    let injector: TestBed;
    let service: ContratoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContrato;
    let expectedResult: IContrato | IContrato[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContratoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Contrato(0, currentDate, currentDate, 'AAAAAAA', false, 'AAAAAAA', 'image/png', 'AAAAAAA', false);
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

      it('should create a Contrato', () => {
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
          .create(new Contrato())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contrato', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            contrato: 'BBBBBB',
            aceitaTermos: true,
            observacao: 'BBBBBB',
            termos: 'BBBBBB',
            emVigor: true
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

      it('should return a list of Contrato', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            contrato: 'BBBBBB',
            aceitaTermos: true,
            observacao: 'BBBBBB',
            termos: 'BBBBBB',
            emVigor: true
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

      it('should delete a Contrato', () => {
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
