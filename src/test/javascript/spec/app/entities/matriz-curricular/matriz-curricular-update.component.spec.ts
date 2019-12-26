import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { MatrizCurricularUpdateComponent } from 'app/entities/matriz-curricular/matriz-curricular-update.component';
import { MatrizCurricularService } from 'app/entities/matriz-curricular/matriz-curricular.service';
import { MatrizCurricular } from 'app/shared/model/matriz-curricular.model';

describe('Component Tests', () => {
  describe('MatrizCurricular Management Update Component', () => {
    let comp: MatrizCurricularUpdateComponent;
    let fixture: ComponentFixture<MatrizCurricularUpdateComponent>;
    let service: MatrizCurricularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [MatrizCurricularUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MatrizCurricularUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatrizCurricularUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatrizCurricularService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatrizCurricular(123);
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
        const entity = new MatrizCurricular();
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
