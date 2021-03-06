import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { MoradaPessoaService } from 'app/entities/morada-pessoa/morada-pessoa.service';
import { IMoradaPessoa, MoradaPessoa } from 'app/shared/model/morada-pessoa.model';

describe('Service Tests', () => {
  describe('MoradaPessoa Service', () => {
    let injector: TestBed;
    let service: MoradaPessoaService;
    let httpMock: HttpTestingController;
    let elemDefault: IMoradaPessoa;
    let expectedResult: IMoradaPessoa | IMoradaPessoa[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MoradaPessoaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MoradaPessoa(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MoradaPessoa', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new MoradaPessoa())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MoradaPessoa', () => {
        const returnedFromService = Object.assign(
          {
            provincia: 'BBBBBB',
            municipio: 'BBBBBB',
            bairro: 'BBBBBB',
            rua: 'BBBBBB',
            quarteirao: 'BBBBBB',
            numeroPorta: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MoradaPessoa', () => {
        const returnedFromService = Object.assign(
          {
            provincia: 'BBBBBB',
            municipio: 'BBBBBB',
            bairro: 'BBBBBB',
            rua: 'BBBBBB',
            quarteirao: 'BBBBBB',
            numeroPorta: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a MoradaPessoa', () => {
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
