import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PlanoAulaService } from 'app/entities/plano-aula/plano-aula.service';
import { IPlanoAula, PlanoAula } from 'app/shared/model/plano-aula.model';

describe('Service Tests', () => {
  describe('PlanoAula Service', () => {
    let injector: TestBed;
    let service: PlanoAulaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlanoAula;
    let expectedResult: IPlanoAula | IPlanoAula[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlanoAulaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PlanoAula(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            tempo: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a PlanoAula', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            tempo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            tempo: currentDate
          },
          returnedFromService
        );
        service
          .create(new PlanoAula())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlanoAula', () => {
        const returnedFromService = Object.assign(
          {
            objectivoGeral: 'BBBBBB',
            objectivoEspecifico: 'BBBBBB',
            conteudo: 'BBBBBB',
            estrategia: 'BBBBBB',
            actividades: 'BBBBBB',
            tempo: currentDate.format(DATE_TIME_FORMAT),
            recursosEnsino: 'BBBBBB',
            avaliacao: 'BBBBBB',
            observacao: 'BBBBBB',
            bibliografia: 'BBBBBB',
            perfilEntrada: 'BBBBBB',
            perfilSaida: 'BBBBBB',
            anexo1: 'BBBBBB',
            anexo2: 'BBBBBB',
            anexo3: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tempo: currentDate
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

      it('should return a list of PlanoAula', () => {
        const returnedFromService = Object.assign(
          {
            objectivoGeral: 'BBBBBB',
            objectivoEspecifico: 'BBBBBB',
            conteudo: 'BBBBBB',
            estrategia: 'BBBBBB',
            actividades: 'BBBBBB',
            tempo: currentDate.format(DATE_TIME_FORMAT),
            recursosEnsino: 'BBBBBB',
            avaliacao: 'BBBBBB',
            observacao: 'BBBBBB',
            bibliografia: 'BBBBBB',
            perfilEntrada: 'BBBBBB',
            perfilSaida: 'BBBBBB',
            anexo1: 'BBBBBB',
            anexo2: 'BBBBBB',
            anexo3: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            tempo: currentDate
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

      it('should delete a PlanoAula', () => {
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
