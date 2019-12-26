import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { EmolumentoUpdateComponent } from 'app/entities/emolumento/emolumento-update.component';
import { EmolumentoService } from 'app/entities/emolumento/emolumento.service';
import { Emolumento } from 'app/shared/model/emolumento.model';

describe('Component Tests', () => {
  describe('Emolumento Management Update Component', () => {
    let comp: EmolumentoUpdateComponent;
    let fixture: ComponentFixture<EmolumentoUpdateComponent>;
    let service: EmolumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [EmolumentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmolumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmolumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmolumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Emolumento(123);
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
        const entity = new Emolumento();
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
