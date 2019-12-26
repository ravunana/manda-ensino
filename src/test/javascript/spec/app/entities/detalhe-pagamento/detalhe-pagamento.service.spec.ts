import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DetalhePagamentoService } from 'app/entities/detalhe-pagamento/detalhe-pagamento.service';
import { IDetalhePagamento, DetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

describe('Service Tests', () => {
  describe('DetalhePagamento Service', () => {
    let injector: TestBed;
    let service: DetalhePagamentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDetalhePagamento;
    let expectedResult: IDetalhePagamento | IDetalhePagamento[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DetalhePagamentoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DetalhePagamento(0, 'AAAAAAA', false, 0, 0, 0, 0, 0, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT),
            vencimento: currentDate.format(DATE_FORMAT)
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

      it('should create a DetalhePagamento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT),
            vencimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            vencimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new DetalhePagamento())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DetalhePagamento', () => {
        const returnedFromService = Object.assign(
          {
            descricao: 'BBBBBB',
            mensalidade: true,
            quantidade: 1,
            valor: 1,
            desconto: 1,
            multa: 1,
            juro: 1,
            data: currentDate.format(DATE_TIME_FORMAT),
            vencimento: currentDate.format(DATE_FORMAT),
            quitado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate,
            vencimento: currentDate
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

      it('should return a list of DetalhePagamento', () => {
        const returnedFromService = Object.assign(
          {
            descricao: 'BBBBBB',
            mensalidade: true,
            quantidade: 1,
            valor: 1,
            desconto: 1,
            multa: 1,
            juro: 1,
            data: currentDate.format(DATE_TIME_FORMAT),
            vencimento: currentDate.format(DATE_FORMAT),
            quitado: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            vencimento: currentDate
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

      it('should delete a DetalhePagamento', () => {
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
