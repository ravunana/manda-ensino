import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CategoriaAlunoService } from 'app/entities/categoria-aluno/categoria-aluno.service';
import { ICategoriaAluno, CategoriaAluno } from 'app/shared/model/categoria-aluno.model';

describe('Service Tests', () => {
  describe('CategoriaAluno Service', () => {
    let injector: TestBed;
    let service: CategoriaAlunoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategoriaAluno;
    let expectedResult: ICategoriaAluno | ICategoriaAluno[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategoriaAlunoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CategoriaAluno(0, 'AAAAAAA', 0, false, false, 'AAAAAAA', 0, false, false);
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

      it('should create a CategoriaAluno', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new CategoriaAluno())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategoriaAluno', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            desconto: 1,
            pagaPropina: true,
            pagaMulta: true,
            descricao: 'BBBBBB',
            diaPagamento: 1,
            mesAtual: true,
            ativo: true
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

      it('should return a list of CategoriaAluno', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            desconto: 1,
            pagaPropina: true,
            pagaMulta: true,
            descricao: 'BBBBBB',
            diaPagamento: 1,
            mesAtual: true,
            ativo: true
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

      it('should delete a CategoriaAluno', () => {
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
