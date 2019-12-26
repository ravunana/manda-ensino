import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaAlunoDetailComponent } from 'app/entities/categoria-aluno/categoria-aluno-detail.component';
import { CategoriaAluno } from 'app/shared/model/categoria-aluno.model';

describe('Component Tests', () => {
  describe('CategoriaAluno Management Detail Component', () => {
    let comp: CategoriaAlunoDetailComponent;
    let fixture: ComponentFixture<CategoriaAlunoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categoriaAluno: new CategoriaAluno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaAlunoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaAlunoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaAlunoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categoriaAluno on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaAluno).toEqual(jasmine.objectContaining({ id: 123 }));
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
