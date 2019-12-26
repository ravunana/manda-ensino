import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DocumentacaoPessoaService } from 'app/entities/documentacao-pessoa/documentacao-pessoa.service';
import { IDocumentacaoPessoa, DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

describe('Service Tests', () => {
  describe('DocumentacaoPessoa Service', () => {
    let injector: TestBed;
    let service: DocumentacaoPessoaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocumentacaoPessoa;
    let expectedResult: IDocumentacaoPessoa | IDocumentacaoPessoa[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocumentacaoPessoaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DocumentacaoPessoa(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            emissao: currentDate.format(DATE_FORMAT),
            validade: currentDate.format(DATE_FORMAT)
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

      it('should create a DocumentacaoPessoa', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            emissao: currentDate.format(DATE_FORMAT),
            validade: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            emissao: currentDate,
            validade: currentDate
          },
          returnedFromService
        );
        service
          .create(new DocumentacaoPessoa())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocumentacaoPessoa', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            numero: 'BBBBBB',
            emissao: currentDate.format(DATE_FORMAT),
            validade: currentDate.format(DATE_FORMAT),
            naturalidade: 'BBBBBB',
            nacionalidade: 'BBBBBB',
            localEmissao: 'BBBBBB',
            nif: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            emissao: currentDate,
            validade: currentDate
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

      it('should return a list of DocumentacaoPessoa', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            numero: 'BBBBBB',
            emissao: currentDate.format(DATE_FORMAT),
            validade: currentDate.format(DATE_FORMAT),
            naturalidade: 'BBBBBB',
            nacionalidade: 'BBBBBB',
            localEmissao: 'BBBBBB',
            nif: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            emissao: currentDate,
            validade: currentDate
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

      it('should delete a DocumentacaoPessoa', () => {
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
