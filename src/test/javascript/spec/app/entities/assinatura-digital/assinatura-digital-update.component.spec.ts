import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { AssinaturaDigitalUpdateComponent } from 'app/entities/assinatura-digital/assinatura-digital-update.component';
import { AssinaturaDigitalService } from 'app/entities/assinatura-digital/assinatura-digital.service';
import { AssinaturaDigital } from 'app/shared/model/assinatura-digital.model';

describe('Component Tests', () => {
  describe('AssinaturaDigital Management Update Component', () => {
    let comp: AssinaturaDigitalUpdateComponent;
    let fixture: ComponentFixture<AssinaturaDigitalUpdateComponent>;
    let service: AssinaturaDigitalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [AssinaturaDigitalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssinaturaDigitalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssinaturaDigitalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssinaturaDigitalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssinaturaDigital(123);
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
        const entity = new AssinaturaDigital();
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
