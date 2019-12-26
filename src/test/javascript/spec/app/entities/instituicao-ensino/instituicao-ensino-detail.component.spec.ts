import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { InstituicaoEnsinoDetailComponent } from 'app/entities/instituicao-ensino/instituicao-ensino-detail.component';
import { InstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

describe('Component Tests', () => {
  describe('InstituicaoEnsino Management Detail Component', () => {
    let comp: InstituicaoEnsinoDetailComponent;
    let fixture: ComponentFixture<InstituicaoEnsinoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ instituicaoEnsino: new InstituicaoEnsino(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [InstituicaoEnsinoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstituicaoEnsinoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstituicaoEnsinoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load instituicaoEnsino on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instituicaoEnsino).toEqual(jasmine.objectContaining({ id: 123 }));
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
