import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaValiacaoDetailComponent } from 'app/entities/categoria-valiacao/categoria-valiacao-detail.component';
import { CategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';

describe('Component Tests', () => {
  describe('CategoriaValiacao Management Detail Component', () => {
    let comp: CategoriaValiacaoDetailComponent;
    let fixture: ComponentFixture<CategoriaValiacaoDetailComponent>;
    const route = ({ data: of({ categoriaValiacao: new CategoriaValiacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaValiacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaValiacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaValiacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoriaValiacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaValiacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
