import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DetalhePagamentoUpdateComponent } from 'app/entities/detalhe-pagamento/detalhe-pagamento-update.component';
import { DetalhePagamentoService } from 'app/entities/detalhe-pagamento/detalhe-pagamento.service';
import { DetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

describe('Component Tests', () => {
  describe('DetalhePagamento Management Update Component', () => {
    let comp: DetalhePagamentoUpdateComponent;
    let fixture: ComponentFixture<DetalhePagamentoUpdateComponent>;
    let service: DetalhePagamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DetalhePagamentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetalhePagamentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalhePagamentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalhePagamentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalhePagamento(123);
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
        const entity = new DetalhePagamento();
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
