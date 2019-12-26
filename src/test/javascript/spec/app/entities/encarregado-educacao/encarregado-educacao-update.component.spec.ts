import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { EncarregadoEducacaoUpdateComponent } from 'app/entities/encarregado-educacao/encarregado-educacao-update.component';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/encarregado-educacao.service';
import { EncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';

describe('Component Tests', () => {
  describe('EncarregadoEducacao Management Update Component', () => {
    let comp: EncarregadoEducacaoUpdateComponent;
    let fixture: ComponentFixture<EncarregadoEducacaoUpdateComponent>;
    let service: EncarregadoEducacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [EncarregadoEducacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EncarregadoEducacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncarregadoEducacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncarregadoEducacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EncarregadoEducacao(123);
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
        const entity = new EncarregadoEducacao();
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
