import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { RequerimentoDetailComponent } from 'app/entities/requerimento/requerimento-detail.component';
import { Requerimento } from 'app/shared/model/requerimento.model';

describe('Component Tests', () => {
  describe('Requerimento Management Detail Component', () => {
    let comp: RequerimentoDetailComponent;
    let fixture: ComponentFixture<RequerimentoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ requerimento: new Requerimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [RequerimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RequerimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequerimentoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load requerimento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requerimento).toEqual(jasmine.objectContaining({ id: 123 }));
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
