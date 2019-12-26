import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CriterioAvaliacaoService } from 'app/entities/criterio-avaliacao/criterio-avaliacao.service';
import { ICriterioAvaliacao, CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

describe('Service Tests', () => {
  describe('CriterioAvaliacao Service', () => {
    let injector: TestBed;
    let service: CriterioAvaliacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICriterioAvaliacao;
    let expectedResult: ICriterioAvaliacao | ICriterioAvaliacao[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CriterioAvaliacaoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CriterioAvaliacao(0, 0, 0, 0, false, false, false, 0, 0, 0, 0);
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

      it('should create a CriterioAvaliacao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new CriterioAvaliacao())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CriterioAvaliacao', () => {
        const returnedFromService = Object.assign(
          {
            aprovaCom: 1,
            reporvaCom: 1,
            recursoCom: 1,
            fazExame: true,
            fazRecurso: true,
            fazExameEspecial: true,
            numeroFaltaReprova: 1,
            menorNota: 1,
            maiorNota: 1,
            notaMinimaAprovacao: 1
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

      it('should return a list of CriterioAvaliacao', () => {
        const returnedFromService = Object.assign(
          {
            aprovaCom: 1,
            reporvaCom: 1,
            recursoCom: 1,
            fazExame: true,
            fazRecurso: true,
            fazExameEspecial: true,
            numeroFaltaReprova: 1,
            menorNota: 1,
            maiorNota: 1,
            notaMinimaAprovacao: 1
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

      it('should delete a CriterioAvaliacao', () => {
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
