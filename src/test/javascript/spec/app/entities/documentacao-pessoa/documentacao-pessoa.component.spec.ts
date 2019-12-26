import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { DocumentacaoPessoaComponent } from 'app/entities/documentacao-pessoa/documentacao-pessoa.component';
import { DocumentacaoPessoaService } from 'app/entities/documentacao-pessoa/documentacao-pessoa.service';
import { DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

describe('Component Tests', () => {
  describe('DocumentacaoPessoa Management Component', () => {
    let comp: DocumentacaoPessoaComponent;
    let fixture: ComponentFixture<DocumentacaoPessoaComponent>;
    let service: DocumentacaoPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DocumentacaoPessoaComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(DocumentacaoPessoaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentacaoPessoaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentacaoPessoaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocumentacaoPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentacaoPessoas && comp.documentacaoPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocumentacaoPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentacaoPessoas && comp.documentacaoPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
