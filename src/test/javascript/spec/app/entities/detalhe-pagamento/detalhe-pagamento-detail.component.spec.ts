import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DetalhePagamentoDetailComponent } from 'app/entities/detalhe-pagamento/detalhe-pagamento-detail.component';
import { DetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

describe('Component Tests', () => {
  describe('DetalhePagamento Management Detail Component', () => {
    let comp: DetalhePagamentoDetailComponent;
    let fixture: ComponentFixture<DetalhePagamentoDetailComponent>;
    const route = ({ data: of({ detalhePagamento: new DetalhePagamento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DetalhePagamentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetalhePagamentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalhePagamentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load detalhePagamento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalhePagamento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
