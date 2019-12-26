import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { MeioLiquidacaoDetailComponent } from 'app/entities/meio-liquidacao/meio-liquidacao-detail.component';
import { MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

describe('Component Tests', () => {
  describe('MeioLiquidacao Management Detail Component', () => {
    let comp: MeioLiquidacaoDetailComponent;
    let fixture: ComponentFixture<MeioLiquidacaoDetailComponent>;
    const route = ({ data: of({ meioLiquidacao: new MeioLiquidacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [MeioLiquidacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeioLiquidacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeioLiquidacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load meioLiquidacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meioLiquidacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
