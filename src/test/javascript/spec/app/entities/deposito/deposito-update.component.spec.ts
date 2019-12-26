import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DepositoUpdateComponent } from 'app/entities/deposito/deposito-update.component';
import { DepositoService } from 'app/entities/deposito/deposito.service';
import { Deposito } from 'app/shared/model/deposito.model';

describe('Component Tests', () => {
  describe('Deposito Management Update Component', () => {
    let comp: DepositoUpdateComponent;
    let fixture: ComponentFixture<DepositoUpdateComponent>;
    let service: DepositoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DepositoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepositoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepositoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepositoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Deposito(123);
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
        const entity = new Deposito();
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
