import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DocumentoMatriculaDetailComponent } from 'app/entities/documento-matricula/documento-matricula-detail.component';
import { DocumentoMatricula } from 'app/shared/model/documento-matricula.model';

describe('Component Tests', () => {
  describe('DocumentoMatricula Management Detail Component', () => {
    let comp: DocumentoMatriculaDetailComponent;
    let fixture: ComponentFixture<DocumentoMatriculaDetailComponent>;
    const route = ({ data: of({ documentoMatricula: new DocumentoMatricula(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DocumentoMatriculaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocumentoMatriculaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentoMatriculaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load documentoMatricula on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentoMatricula).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
