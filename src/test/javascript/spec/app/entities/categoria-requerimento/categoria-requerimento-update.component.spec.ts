import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaRequerimentoUpdateComponent } from 'app/entities/categoria-requerimento/categoria-requerimento-update.component';
import { CategoriaRequerimentoService } from 'app/entities/categoria-requerimento/categoria-requerimento.service';
import { CategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';

describe('Component Tests', () => {
  describe('CategoriaRequerimento Management Update Component', () => {
    let comp: CategoriaRequerimentoUpdateComponent;
    let fixture: ComponentFixture<CategoriaRequerimentoUpdateComponent>;
    let service: CategoriaRequerimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaRequerimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaRequerimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaRequerimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaRequerimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaRequerimento(123);
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
        const entity = new CategoriaRequerimento();
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
