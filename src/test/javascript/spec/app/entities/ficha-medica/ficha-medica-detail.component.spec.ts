import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { FichaMedicaDetailComponent } from 'app/entities/ficha-medica/ficha-medica-detail.component';
import { FichaMedica } from 'app/shared/model/ficha-medica.model';

describe('Component Tests', () => {
  describe('FichaMedica Management Detail Component', () => {
    let comp: FichaMedicaDetailComponent;
    let fixture: ComponentFixture<FichaMedicaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ fichaMedica: new FichaMedica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [FichaMedicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichaMedicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaMedicaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load fichaMedica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fichaMedica).toEqual(jasmine.objectContaining({ id: 123 }));
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
