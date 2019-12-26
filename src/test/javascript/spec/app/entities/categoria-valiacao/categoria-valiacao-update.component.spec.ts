import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaValiacaoUpdateComponent } from 'app/entities/categoria-valiacao/categoria-valiacao-update.component';
import { CategoriaValiacaoService } from 'app/entities/categoria-valiacao/categoria-valiacao.service';
import { CategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';

describe('Component Tests', () => {
  describe('CategoriaValiacao Management Update Component', () => {
    let comp: CategoriaValiacaoUpdateComponent;
    let fixture: ComponentFixture<CategoriaValiacaoUpdateComponent>;
    let service: CategoriaValiacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaValiacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaValiacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaValiacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaValiacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaValiacao(123);
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
        const entity = new CategoriaValiacao();
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
