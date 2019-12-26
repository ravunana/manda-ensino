import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DossificacaoUpdateComponent } from 'app/entities/dossificacao/dossificacao-update.component';
import { DossificacaoService } from 'app/entities/dossificacao/dossificacao.service';
import { Dossificacao } from 'app/shared/model/dossificacao.model';

describe('Component Tests', () => {
  describe('Dossificacao Management Update Component', () => {
    let comp: DossificacaoUpdateComponent;
    let fixture: ComponentFixture<DossificacaoUpdateComponent>;
    let service: DossificacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DossificacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DossificacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DossificacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DossificacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dossificacao(123);
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
        const entity = new Dossificacao();
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
