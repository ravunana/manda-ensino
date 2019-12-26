import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaRequerimentoDetailComponent } from 'app/entities/categoria-requerimento/categoria-requerimento-detail.component';
import { CategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';

describe('Component Tests', () => {
  describe('CategoriaRequerimento Management Detail Component', () => {
    let comp: CategoriaRequerimentoDetailComponent;
    let fixture: ComponentFixture<CategoriaRequerimentoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categoriaRequerimento: new CategoriaRequerimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaRequerimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaRequerimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaRequerimentoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categoriaRequerimento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaRequerimento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
