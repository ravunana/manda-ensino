import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DocumentacaoPessoaUpdateComponent } from 'app/entities/documentacao-pessoa/documentacao-pessoa-update.component';
import { DocumentacaoPessoaService } from 'app/entities/documentacao-pessoa/documentacao-pessoa.service';
import { DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

describe('Component Tests', () => {
  describe('DocumentacaoPessoa Management Update Component', () => {
    let comp: DocumentacaoPessoaUpdateComponent;
    let fixture: ComponentFixture<DocumentacaoPessoaUpdateComponent>;
    let service: DocumentacaoPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DocumentacaoPessoaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocumentacaoPessoaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentacaoPessoaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentacaoPessoaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentacaoPessoa(123);
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
        const entity = new DocumentacaoPessoa();
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
