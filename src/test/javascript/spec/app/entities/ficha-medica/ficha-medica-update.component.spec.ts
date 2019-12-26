import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { FichaMedicaUpdateComponent } from 'app/entities/ficha-medica/ficha-medica-update.component';
import { FichaMedicaService } from 'app/entities/ficha-medica/ficha-medica.service';
import { FichaMedica } from 'app/shared/model/ficha-medica.model';

describe('Component Tests', () => {
  describe('FichaMedica Management Update Component', () => {
    let comp: FichaMedicaUpdateComponent;
    let fixture: ComponentFixture<FichaMedicaUpdateComponent>;
    let service: FichaMedicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [FichaMedicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FichaMedicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaMedicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaMedicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FichaMedica(123);
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
        const entity = new FichaMedica();
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
