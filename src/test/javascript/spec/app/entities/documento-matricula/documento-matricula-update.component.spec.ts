import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DocumentoMatriculaUpdateComponent } from 'app/entities/documento-matricula/documento-matricula-update.component';
import { DocumentoMatriculaService } from 'app/entities/documento-matricula/documento-matricula.service';
import { DocumentoMatricula } from 'app/shared/model/documento-matricula.model';

describe('Component Tests', () => {
  describe('DocumentoMatricula Management Update Component', () => {
    let comp: DocumentoMatriculaUpdateComponent;
    let fixture: ComponentFixture<DocumentoMatriculaUpdateComponent>;
    let service: DocumentoMatriculaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DocumentoMatriculaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocumentoMatriculaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentoMatriculaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentoMatriculaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentoMatricula(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentoMatricula();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
