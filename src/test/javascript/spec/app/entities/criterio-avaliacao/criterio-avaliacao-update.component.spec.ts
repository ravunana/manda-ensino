import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CriterioAvaliacaoUpdateComponent } from 'app/entities/criterio-avaliacao/criterio-avaliacao-update.component';
import { CriterioAvaliacaoService } from 'app/entities/criterio-avaliacao/criterio-avaliacao.service';
import { CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

describe('Component Tests', () => {
  describe('CriterioAvaliacao Management Update Component', () => {
    let comp: CriterioAvaliacaoUpdateComponent;
    let fixture: ComponentFixture<CriterioAvaliacaoUpdateComponent>;
    let service: CriterioAvaliacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CriterioAvaliacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CriterioAvaliacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CriterioAvaliacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CriterioAvaliacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CriterioAvaliacao(123);
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
        const entity = new CriterioAvaliacao();
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
