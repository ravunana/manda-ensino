import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { FormaLiquidacaoDetailComponent } from 'app/entities/forma-liquidacao/forma-liquidacao-detail.component';
import { FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

describe('Component Tests', () => {
  describe('FormaLiquidacao Management Detail Component', () => {
    let comp: FormaLiquidacaoDetailComponent;
    let fixture: ComponentFixture<FormaLiquidacaoDetailComponent>;
    const route = ({ data: of({ formaLiquidacao: new FormaLiquidacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [FormaLiquidacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormaLiquidacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormaLiquidacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load formaLiquidacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formaLiquidacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
