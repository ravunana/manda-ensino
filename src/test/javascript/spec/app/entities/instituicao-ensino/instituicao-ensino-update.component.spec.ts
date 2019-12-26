import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { InstituicaoEnsinoUpdateComponent } from 'app/entities/instituicao-ensino/instituicao-ensino-update.component';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';
import { InstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

describe('Component Tests', () => {
  describe('InstituicaoEnsino Management Update Component', () => {
    let comp: InstituicaoEnsinoUpdateComponent;
    let fixture: ComponentFixture<InstituicaoEnsinoUpdateComponent>;
    let service: InstituicaoEnsinoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [InstituicaoEnsinoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InstituicaoEnsinoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstituicaoEnsinoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstituicaoEnsinoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InstituicaoEnsino(123);
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
        const entity = new InstituicaoEnsino();
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
