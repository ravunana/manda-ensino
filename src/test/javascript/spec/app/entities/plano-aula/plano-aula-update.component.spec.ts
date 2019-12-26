import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { PlanoAulaUpdateComponent } from 'app/entities/plano-aula/plano-aula-update.component';
import { PlanoAulaService } from 'app/entities/plano-aula/plano-aula.service';
import { PlanoAula } from 'app/shared/model/plano-aula.model';

describe('Component Tests', () => {
  describe('PlanoAula Management Update Component', () => {
    let comp: PlanoAulaUpdateComponent;
    let fixture: ComponentFixture<PlanoAulaUpdateComponent>;
    let service: PlanoAulaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [PlanoAulaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanoAulaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoAulaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoAulaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoAula(123);
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
        const entity = new PlanoAula();
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
