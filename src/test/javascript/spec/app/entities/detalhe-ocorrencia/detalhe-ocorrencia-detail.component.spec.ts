import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { DetalheOcorrenciaDetailComponent } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia-detail.component';
import { DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

describe('Component Tests', () => {
  describe('DetalheOcorrencia Management Detail Component', () => {
    let comp: DetalheOcorrenciaDetailComponent;
    let fixture: ComponentFixture<DetalheOcorrenciaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ detalheOcorrencia: new DetalheOcorrencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DetalheOcorrenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetalheOcorrenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalheOcorrenciaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load detalheOcorrencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalheOcorrencia).toEqual(jasmine.objectContaining({ id: 123 }));
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
