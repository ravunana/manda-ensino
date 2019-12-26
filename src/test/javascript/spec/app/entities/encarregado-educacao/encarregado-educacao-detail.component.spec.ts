import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { EncarregadoEducacaoDetailComponent } from 'app/entities/encarregado-educacao/encarregado-educacao-detail.component';
import { EncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';

describe('Component Tests', () => {
  describe('EncarregadoEducacao Management Detail Component', () => {
    let comp: EncarregadoEducacaoDetailComponent;
    let fixture: ComponentFixture<EncarregadoEducacaoDetailComponent>;
    const route = ({ data: of({ encarregadoEducacao: new EncarregadoEducacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [EncarregadoEducacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EncarregadoEducacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncarregadoEducacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encarregadoEducacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encarregadoEducacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
