import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IAluno, Aluno } from 'app/shared/model/aluno.model';

describe('Service Tests', () => {
  describe('Aluno Service', () => {
    let injector: TestBed;
    let service: AlunoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAluno;
    let expectedResult: IAluno | IAluno[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlunoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Aluno(0, 'AAAAAAA', false, currentDate, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Aluno', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new Aluno())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Aluno', () => {
        const returnedFromService = Object.assign(
          {
            numeroProcesso: 'BBBBBB',
            transferido: true,
            data: currentDate.format(DATE_TIME_FORMAT),
            turmaAnterior: 'BBBBBB',
            anoConclusao: 1,
            cursoFrequentado: 'BBBBBB',
            nomeEscolaAnteriror: 'BBBBBB',
            enderecoEscolaAnterior: 'BBBBBB',
            classeConcluida: 1,
            numeroProcessoAnterior: 'BBBBBB',
            situacaoAnterior: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
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

      it('should return a list of Aluno', () => {
        const returnedFromService = Object.assign(
          {
            numeroProcesso: 'BBBBBB',
            transferido: true,
            data: currentDate.format(DATE_TIME_FORMAT),
            turmaAnterior: 'BBBBBB',
            anoConclusao: 1,
            cursoFrequentado: 'BBBBBB',
            nomeEscolaAnteriror: 'BBBBBB',
            enderecoEscolaAnterior: 'BBBBBB',
            classeConcluida: 1,
            numeroProcessoAnterior: 'BBBBBB',
            situacaoAnterior: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
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

      it('should delete a Aluno', () => {
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
