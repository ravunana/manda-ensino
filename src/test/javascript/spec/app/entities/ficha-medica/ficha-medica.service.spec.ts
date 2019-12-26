import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FichaMedicaService } from 'app/entities/ficha-medica/ficha-medica.service';
import { IFichaMedica, FichaMedica } from 'app/shared/model/ficha-medica.model';

describe('Service Tests', () => {
  describe('FichaMedica Service', () => {
    let injector: TestBed;
    let service: FichaMedicaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFichaMedica;
    let expectedResult: IFichaMedica | IFichaMedica[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FichaMedicaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FichaMedica(0, false, 'AAAAAAA', 0, 0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA');
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

      it('should create a FichaMedica', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new FichaMedica())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FichaMedica', () => {
        const returnedFromService = Object.assign(
          {
            fazEducacaoFisica: true,
            grupoSanguinio: 'BBBBBB',
            altura: 1,
            peso: 1,
            autorizaMedicamento: true,
            observacao: 'BBBBBB',
            nomeMedico: 'BBBBBB',
            contactoMedico: 'BBBBBB',
            desmaioConstante: true,
            complicacoesSaude: 'BBBBBB'
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

      it('should return a list of FichaMedica', () => {
        const returnedFromService = Object.assign(
          {
            fazEducacaoFisica: true,
            grupoSanguinio: 'BBBBBB',
            altura: 1,
            peso: 1,
            autorizaMedicamento: true,
            observacao: 'BBBBBB',
            nomeMedico: 'BBBBBB',
            contactoMedico: 'BBBBBB',
            desmaioConstante: true,
            complicacoesSaude: 'BBBBBB'
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

      it('should delete a FichaMedica', () => {
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
