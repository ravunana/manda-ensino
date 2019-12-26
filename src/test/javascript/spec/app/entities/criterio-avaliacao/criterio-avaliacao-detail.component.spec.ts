import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CriterioAvaliacaoDetailComponent } from 'app/entities/criterio-avaliacao/criterio-avaliacao-detail.component';
import { CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

describe('Component Tests', () => {
  describe('CriterioAvaliacao Management Detail Component', () => {
    let comp: CriterioAvaliacaoDetailComponent;
    let fixture: ComponentFixture<CriterioAvaliacaoDetailComponent>;
    const route = ({ data: of({ criterioAvaliacao: new CriterioAvaliacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CriterioAvaliacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CriterioAvaliacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CriterioAvaliacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load criterioAvaliacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.criterioAvaliacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
