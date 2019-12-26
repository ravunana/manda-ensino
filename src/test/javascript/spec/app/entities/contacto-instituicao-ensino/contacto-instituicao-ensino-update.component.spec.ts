import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { ContactoInstituicaoEnsinoUpdateComponent } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino-update.component';
import { ContactoInstituicaoEnsinoService } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino.service';
import { ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('ContactoInstituicaoEnsino Management Update Component', () => {
    let comp: ContactoInstituicaoEnsinoUpdateComponent;
    let fixture: ComponentFixture<ContactoInstituicaoEnsinoUpdateComponent>;
    let service: ContactoInstituicaoEnsinoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [ContactoInstituicaoEnsinoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContactoInstituicaoEnsinoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactoInstituicaoEnsinoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoInstituicaoEnsinoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactoInstituicaoEnsino(123);
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
        const entity = new ContactoInstituicaoEnsino();
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
