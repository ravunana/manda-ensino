import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { UnidadeCurricularUpdateComponent } from 'app/entities/unidade-curricular/unidade-curricular-update.component';
import { UnidadeCurricularService } from 'app/entities/unidade-curricular/unidade-curricular.service';
import { UnidadeCurricular } from 'app/shared/model/unidade-curricular.model';

describe('Component Tests', () => {
  describe('UnidadeCurricular Management Update Component', () => {
    let comp: UnidadeCurricularUpdateComponent;
    let fixture: ComponentFixture<UnidadeCurricularUpdateComponent>;
    let service: UnidadeCurricularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [UnidadeCurricularUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UnidadeCurricularUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnidadeCurricularUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnidadeCurricularService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UnidadeCurricular(123);
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
        const entity = new UnidadeCurricular();
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
