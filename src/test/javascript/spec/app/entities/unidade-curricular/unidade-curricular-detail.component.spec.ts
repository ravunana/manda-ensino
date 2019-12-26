import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { UnidadeCurricularDetailComponent } from 'app/entities/unidade-curricular/unidade-curricular-detail.component';
import { UnidadeCurricular } from 'app/shared/model/unidade-curricular.model';

describe('Component Tests', () => {
  describe('UnidadeCurricular Management Detail Component', () => {
    let comp: UnidadeCurricularDetailComponent;
    let fixture: ComponentFixture<UnidadeCurricularDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ unidadeCurricular: new UnidadeCurricular(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [UnidadeCurricularDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnidadeCurricularDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnidadeCurricularDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load unidadeCurricular on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unidadeCurricular).toEqual(jasmine.objectContaining({ id: 123 }));
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
