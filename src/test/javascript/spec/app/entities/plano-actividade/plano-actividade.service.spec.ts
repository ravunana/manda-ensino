import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PlanoActividadeService } from 'app/entities/plano-actividade/plano-actividade.service';
import { IPlanoActividade, PlanoActividade } from 'app/shared/model/plano-actividade.model';

describe('Service Tests', () => {
  describe('PlanoActividade Service', () => {
    let injector: TestBed;
    let service: PlanoActividadeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlanoActividade;
    let expectedResult: IPlanoActividade | IPlanoActividade[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlanoActividadeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PlanoActividade(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
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

      it('should create a PlanoActividade', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            anoLectivo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate,
            anoLectivo: currentDate
          },
          returnedFromService
        );
        service
          .create(new PlanoActividade())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlanoActividade', () => {
        const returnedFromService = Object.assign(
          {
            numeroActividade: 1,
            atividade: 'BBBBBB',
            objectivos: 'BBBBBB',
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            responsavel: 'BBBBBB',
            local: 'BBBBBB',
            observacao: 'BBBBBB',
            participantes: 'BBBBBB',
            coResponsavel: 'BBBBBB',
            statusActividade: 'BBBBBB',
            anoLectivo: currentDate.format(DATE_FORMAT),
            periodoLectivo: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate,
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

      it('should return a list of PlanoActividade', () => {
        const returnedFromService = Object.assign(
          {
            numeroActividade: 1,
            atividade: 'BBBBBB',
            objectivos: 'BBBBBB',
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            responsavel: 'BBBBBB',
            local: 'BBBBBB',
            observacao: 'BBBBBB',
            participantes: 'BBBBBB',
            coResponsavel: 'BBBBBB',
            statusActividade: 'BBBBBB',
            anoLectivo: currentDate.format(DATE_FORMAT),
            periodoLectivo: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            de: currentDate,
            ate: currentDate,
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

      it('should delete a PlanoActividade', () => {
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
