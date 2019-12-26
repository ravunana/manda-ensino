import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { LocalizacaoInstituicaoEnsinoUpdateComponent } from 'app/entities/localizacao-instituicao-ensino/localizacao-instituicao-ensino-update.component';
import { LocalizacaoInstituicaoEnsinoService } from 'app/entities/localizacao-instituicao-ensino/localizacao-instituicao-ensino.service';
import { LocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('LocalizacaoInstituicaoEnsino Management Update Component', () => {
    let comp: LocalizacaoInstituicaoEnsinoUpdateComponent;
    let fixture: ComponentFixture<LocalizacaoInstituicaoEnsinoUpdateComponent>;
    let service: LocalizacaoInstituicaoEnsinoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [LocalizacaoInstituicaoEnsinoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocalizacaoInstituicaoEnsinoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalizacaoInstituicaoEnsinoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizacaoInstituicaoEnsinoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocalizacaoInstituicaoEnsino(123);
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
        const entity = new LocalizacaoInstituicaoEnsino();
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
