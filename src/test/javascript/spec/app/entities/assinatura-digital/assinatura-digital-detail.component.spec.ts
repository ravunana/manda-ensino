import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { AssinaturaDigitalDetailComponent } from 'app/entities/assinatura-digital/assinatura-digital-detail.component';
import { AssinaturaDigital } from 'app/shared/model/assinatura-digital.model';

describe('Component Tests', () => {
  describe('AssinaturaDigital Management Detail Component', () => {
    let comp: AssinaturaDigitalDetailComponent;
    let fixture: ComponentFixture<AssinaturaDigitalDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ assinaturaDigital: new AssinaturaDigital(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [AssinaturaDigitalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssinaturaDigitalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssinaturaDigitalDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load assinaturaDigital on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assinaturaDigital).toEqual(jasmine.objectContaining({ id: 123 }));
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
