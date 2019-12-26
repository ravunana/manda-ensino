import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { PessoaDetailComponent } from 'app/entities/pessoa/pessoa-detail.component';
import { Pessoa } from 'app/shared/model/pessoa.model';

describe('Component Tests', () => {
  describe('Pessoa Management Detail Component', () => {
    let comp: PessoaDetailComponent;
    let fixture: ComponentFixture<PessoaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ pessoa: new Pessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [PessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PessoaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load pessoa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pessoa).toEqual(jasmine.objectContaining({ id: 123 }));
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
