import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { RequerimentoUpdateComponent } from 'app/entities/requerimento/requerimento-update.component';
import { RequerimentoService } from 'app/entities/requerimento/requerimento.service';
import { Requerimento } from 'app/shared/model/requerimento.model';

describe('Component Tests', () => {
  describe('Requerimento Management Update Component', () => {
    let comp: RequerimentoUpdateComponent;
    let fixture: ComponentFixture<RequerimentoUpdateComponent>;
    let service: RequerimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [RequerimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RequerimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequerimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequerimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Requerimento(123);
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
        const entity = new Requerimento();
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
