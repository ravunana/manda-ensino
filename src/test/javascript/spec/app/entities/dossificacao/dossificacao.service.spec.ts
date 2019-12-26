import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DossificacaoService } from 'app/entities/dossificacao/dossificacao.service';
import { IDossificacao, Dossificacao } from 'app/shared/model/dossificacao.model';

describe('Service Tests', () => {
  describe('Dossificacao Service', () => {
    let injector: TestBed;
    let service: DossificacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDossificacao;
    let expectedResult: IDossificacao | IDossificacao[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DossificacaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Dossificacao(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        currentDate,
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
            anoLectivo: currentDate.format(DATE_FORMAT),
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            tempoAula: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Dossificacao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            anoLectivo: currentDate.format(DATE_FORMAT),
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            tempoAula: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            anoLectivo: currentDate,
            de: currentDate,
            ate: currentDate,
            tempoAula: currentDate
          },
          returnedFromService
        );
        service
          .create(new Dossificacao())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dossificacao', () => {
        const returnedFromService = Object.assign(
          {
            periodoLectivo: 'BBBBBB',
            anoLectivo: currentDate.format(DATE_FORMAT),
            objectivoGeral: 'BBBBBB',
            objectivoEspecifico: 'BBBBBB',
            semanaLectiva: 1,
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            conteudo: 'BBBBBB',
            procedimentoEnsino: 'BBBBBB',
            recursosDidatico: 'BBBBBB',
            tempoAula: currentDate.format(DATE_TIME_FORMAT),
            formaAvaliacao: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            anoLectivo: currentDate,
            de: currentDate,
            ate: currentDate,
            tempoAula: currentDate
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

      it('should return a list of Dossificacao', () => {
        const returnedFromService = Object.assign(
          {
            periodoLectivo: 'BBBBBB',
            anoLectivo: currentDate.format(DATE_FORMAT),
            objectivoGeral: 'BBBBBB',
            objectivoEspecifico: 'BBBBBB',
            semanaLectiva: 1,
            de: currentDate.format(DATE_FORMAT),
            ate: currentDate.format(DATE_FORMAT),
            conteudo: 'BBBBBB',
            procedimentoEnsino: 'BBBBBB',
            recursosDidatico: 'BBBBBB',
            tempoAula: currentDate.format(DATE_TIME_FORMAT),
            formaAvaliacao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            anoLectivo: currentDate,
            de: currentDate,
            ate: currentDate,
            tempoAula: currentDate
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

      it('should delete a Dossificacao', () => {
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
