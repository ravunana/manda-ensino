import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { PlanoCurricularUpdateComponent } from 'app/entities/plano-curricular/plano-curricular-update.component';
import { PlanoCurricularService } from 'app/entities/plano-curricular/plano-curricular.service';
import { PlanoCurricular } from 'app/shared/model/plano-curricular.model';

describe('Component Tests', () => {
  describe('PlanoCurricular Management Update Component', () => {
    let comp: PlanoCurricularUpdateComponent;
    let fixture: ComponentFixture<PlanoCurricularUpdateComponent>;
    let service: PlanoCurricularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [PlanoCurricularUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanoCurricularUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoCurricularUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoCurricularService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoCurricular(123);
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
        const entity = new PlanoCurricular();
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
