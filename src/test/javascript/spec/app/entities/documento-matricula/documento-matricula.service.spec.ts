import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DocumentoMatriculaService } from 'app/entities/documento-matricula/documento-matricula.service';
import { IDocumentoMatricula, DocumentoMatricula } from 'app/shared/model/documento-matricula.model';

describe('Service Tests', () => {
  describe('DocumentoMatricula Service', () => {
    let injector: TestBed;
    let service: DocumentoMatriculaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocumentoMatricula;
    let expectedResult: IDocumentoMatricula | IDocumentoMatricula[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocumentoMatriculaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DocumentoMatricula(0, false, false, false, false, false, false, false, false, false, 'AAAAAAA', 0, currentDate);
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

      it('should create a DocumentoMatricula', () => {
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
          .create(new DocumentoMatricula())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocumentoMatricula', () => {
        const returnedFromService = Object.assign(
          {
            fotografia: true,
            certificado: true,
            bilhete: true,
            resenciamentoMilitar: true,
            cartaoVacina: true,
            atestadoMedico: true,
            fichaTrnasferencia: true,
            historicoEscolar: true,
            cedula: true,
            descricao: 'BBBBBB',
            anoLectivo: 1,
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
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DocumentoMatricula', () => {
        const returnedFromService = Object.assign(
          {
            fotografia: true,
            certificado: true,
            bilhete: true,
            resenciamentoMilitar: true,
            cartaoVacina: true,
            atestadoMedico: true,
            fichaTrnasferencia: true,
            historicoEscolar: true,
            cedula: true,
            descricao: 'BBBBBB',
            anoLectivo: 1,
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

      it('should delete a DocumentoMatricula', () => {
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
