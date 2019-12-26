import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { PlanoActividadeUpdateComponent } from 'app/entities/plano-actividade/plano-actividade-update.component';
import { PlanoActividadeService } from 'app/entities/plano-actividade/plano-actividade.service';
import { PlanoActividade } from 'app/shared/model/plano-actividade.model';

describe('Component Tests', () => {
  describe('PlanoActividade Management Update Component', () => {
    let comp: PlanoActividadeUpdateComponent;
    let fixture: ComponentFixture<PlanoActividadeUpdateComponent>;
    let service: PlanoActividadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [PlanoActividadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanoActividadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoActividadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoActividadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoActividade(123);
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
        const entity = new PlanoActividade();
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
