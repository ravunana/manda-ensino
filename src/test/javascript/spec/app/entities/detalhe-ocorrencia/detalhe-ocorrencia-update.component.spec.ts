import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DetalheOcorrenciaUpdateComponent } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia-update.component';
import { DetalheOcorrenciaService } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia.service';
import { DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

describe('Component Tests', () => {
  describe('DetalheOcorrencia Management Update Component', () => {
    let comp: DetalheOcorrenciaUpdateComponent;
    let fixture: ComponentFixture<DetalheOcorrenciaUpdateComponent>;
    let service: DetalheOcorrenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DetalheOcorrenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetalheOcorrenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalheOcorrenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalheOcorrenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalheOcorrencia(123);
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
        const entity = new DetalheOcorrencia();
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
