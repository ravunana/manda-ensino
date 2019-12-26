import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { SituacaoAcademicaUpdateComponent } from 'app/entities/situacao-academica/situacao-academica-update.component';
import { SituacaoAcademicaService } from 'app/entities/situacao-academica/situacao-academica.service';
import { SituacaoAcademica } from 'app/shared/model/situacao-academica.model';

describe('Component Tests', () => {
  describe('SituacaoAcademica Management Update Component', () => {
    let comp: SituacaoAcademicaUpdateComponent;
    let fixture: ComponentFixture<SituacaoAcademicaUpdateComponent>;
    let service: SituacaoAcademicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [SituacaoAcademicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SituacaoAcademicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SituacaoAcademicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SituacaoAcademicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SituacaoAcademica(123);
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
        const entity = new SituacaoAcademica();
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
