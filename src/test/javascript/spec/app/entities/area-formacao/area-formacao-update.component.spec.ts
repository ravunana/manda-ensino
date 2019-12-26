import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { AreaFormacaoUpdateComponent } from 'app/entities/area-formacao/area-formacao-update.component';
import { AreaFormacaoService } from 'app/entities/area-formacao/area-formacao.service';
import { AreaFormacao } from 'app/shared/model/area-formacao.model';

describe('Component Tests', () => {
  describe('AreaFormacao Management Update Component', () => {
    let comp: AreaFormacaoUpdateComponent;
    let fixture: ComponentFixture<AreaFormacaoUpdateComponent>;
    let service: AreaFormacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [AreaFormacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AreaFormacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AreaFormacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AreaFormacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AreaFormacao(123);
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
        const entity = new AreaFormacao();
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
