import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { SituacaoAcademicaDetailComponent } from 'app/entities/situacao-academica/situacao-academica-detail.component';
import { SituacaoAcademica } from 'app/shared/model/situacao-academica.model';

describe('Component Tests', () => {
  describe('SituacaoAcademica Management Detail Component', () => {
    let comp: SituacaoAcademicaDetailComponent;
    let fixture: ComponentFixture<SituacaoAcademicaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ situacaoAcademica: new SituacaoAcademica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [SituacaoAcademicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SituacaoAcademicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SituacaoAcademicaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load situacaoAcademica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.situacaoAcademica).toEqual(jasmine.objectContaining({ id: 123 }));
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
