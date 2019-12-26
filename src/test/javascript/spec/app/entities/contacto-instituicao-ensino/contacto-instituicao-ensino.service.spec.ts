import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ContactoInstituicaoEnsinoService } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino.service';
import { IContactoInstituicaoEnsino, ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

describe('Service Tests', () => {
  describe('ContactoInstituicaoEnsino Service', () => {
    let injector: TestBed;
    let service: ContactoInstituicaoEnsinoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContactoInstituicaoEnsino;
    let expectedResult: IContactoInstituicaoEnsino | IContactoInstituicaoEnsino[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContactoInstituicaoEnsinoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ContactoInstituicaoEnsino(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a ContactoInstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ContactoInstituicaoEnsino())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ContactoInstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            tipoContacto: 'BBBBBB',
            descricao: 'BBBBBB',
            contacto: 'BBBBBB'
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

      it('should return a list of ContactoInstituicaoEnsino', () => {
        const returnedFromService = Object.assign(
          {
            tipoContacto: 'BBBBBB',
            descricao: 'BBBBBB',
            contacto: 'BBBBBB'
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

      it('should delete a ContactoInstituicaoEnsino', () => {
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
