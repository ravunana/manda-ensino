import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { RelacionamentoPessoaComponent } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa.component';
import { RelacionamentoPessoaService } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa.service';
import { RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

describe('Component Tests', () => {
  describe('RelacionamentoPessoa Management Component', () => {
    let comp: RelacionamentoPessoaComponent;
    let fixture: ComponentFixture<RelacionamentoPessoaComponent>;
    let service: RelacionamentoPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [RelacionamentoPessoaComponent],
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
        .overrideTemplate(RelacionamentoPessoaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelacionamentoPessoaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelacionamentoPessoaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RelacionamentoPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.relacionamentoPessoas && comp.relacionamentoPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RelacionamentoPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.relacionamentoPessoas && comp.relacionamentoPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
