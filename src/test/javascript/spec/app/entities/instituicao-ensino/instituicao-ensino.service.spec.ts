import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';
import { IInstituicaoEnsino, InstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

describe('Service Tests', () => {
  describe('InstituicaoEnsino Service', () => {
    let injector: TestBed;
    let service: InstituicaoEnsinoService;
    let httpMock: HttpTestingController;
    let elemDefault: IInstituicaoEnsino;
    let expectedResult: IInstituicaoEnsino | IInstituicaoEnsino[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InstituicaoEnsinoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InstituicaoEnsino(
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fundacao: currentDate.format(DATE_FORMAT)
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

      it('should create a InstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fundacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new InstituicaoEnsino())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            logotipo: 'BBBBBB',
            fundacao: currentDate.format(DATE_FORMAT),
            numero: 'BBBBBB',
            tipoVinculo: 'BBBBBB',
            unidadePagadora: 'BBBBBB',
            unidadeOrganica: 'BBBBBB',
            tipoInstalacao: 'BBBBBB',
            dimensao: 'BBBBBB',
            carimbo: 'BBBBBB',
            sede: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fundacao: currentDate
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

      it('should return a list of InstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            logotipo: 'BBBBBB',
            fundacao: currentDate.format(DATE_FORMAT),
            numero: 'BBBBBB',
            tipoVinculo: 'BBBBBB',
            unidadePagadora: 'BBBBBB',
            unidadeOrganica: 'BBBBBB',
            tipoInstalacao: 'BBBBBB',
            dimensao: 'BBBBBB',
            carimbo: 'BBBBBB',
            sede: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacao: currentDate
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

      it('should delete a InstituicaoEnsino', () => {
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
