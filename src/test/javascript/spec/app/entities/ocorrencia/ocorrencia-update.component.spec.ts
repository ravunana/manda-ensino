import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { OcorrenciaUpdateComponent } from 'app/entities/ocorrencia/ocorrencia-update.component';
import { OcorrenciaService } from 'app/entities/ocorrencia/ocorrencia.service';
import { Ocorrencia } from 'app/shared/model/ocorrencia.model';

describe('Component Tests', () => {
  describe('Ocorrencia Management Update Component', () => {
    let comp: OcorrenciaUpdateComponent;
    let fixture: ComponentFixture<OcorrenciaUpdateComponent>;
    let service: OcorrenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [OcorrenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OcorrenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OcorrenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OcorrenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ocorrencia(123);
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
        const entity = new Ocorrencia();
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
