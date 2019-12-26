import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaAlunoUpdateComponent } from 'app/entities/categoria-aluno/categoria-aluno-update.component';
import { CategoriaAlunoService } from 'app/entities/categoria-aluno/categoria-aluno.service';
import { CategoriaAluno } from 'app/shared/model/categoria-aluno.model';

describe('Component Tests', () => {
  describe('CategoriaAluno Management Update Component', () => {
    let comp: CategoriaAlunoUpdateComponent;
    let fixture: ComponentFixture<CategoriaAlunoUpdateComponent>;
    let service: CategoriaAlunoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaAlunoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaAlunoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaAlunoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaAlunoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaAluno(123);
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
        const entity = new CategoriaAluno();
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
