import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { IPessoa, Pessoa } from 'app/shared/model/pessoa.model';

describe('Service Tests', () => {
  describe('Pessoa Service', () => {
    let injector: TestBed;
    let service: PessoaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPessoa;
    let expectedResult: IPessoa | IPessoa[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PessoaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pessoa(0, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            nascimento: currentDate.format(DATE_FORMAT)
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

      it('should create a Pessoa', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            nascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            nascimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Pessoa())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pessoa', () => {
        const returnedFromService = Object.assign(
          {
            tipoPessoa: 'BBBBBB',
            nome: 'BBBBBB',
            imagem: 'BBBBBB',
            pai: 'BBBBBB',
            mae: 'BBBBBB',
            nascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nascimento: currentDate
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

      it('should return a list of Pessoa', () => {
        const returnedFromService = Object.assign(
          {
            tipoPessoa: 'BBBBBB',
            nome: 'BBBBBB',
            imagem: 'BBBBBB',
            pai: 'BBBBBB',
            mae: 'BBBBBB',
            nascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            nascimento: currentDate
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

      it('should delete a Pessoa', () => {
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
