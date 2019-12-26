import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { CategoriaRequerimentoComponent } from 'app/entities/categoria-requerimento/categoria-requerimento.component';
import { CategoriaRequerimentoService } from 'app/entities/categoria-requerimento/categoria-requerimento.service';
import { CategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';

describe('Component Tests', () => {
  describe('CategoriaRequerimento Management Component', () => {
    let comp: CategoriaRequerimentoComponent;
    let fixture: ComponentFixture<CategoriaRequerimentoComponent>;
    let service: CategoriaRequerimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaRequerimentoComponent],
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
        .overrideTemplate(CategoriaRequerimentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaRequerimentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaRequerimentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategoriaRequerimento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categoriaRequerimentos && comp.categoriaRequerimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategoriaRequerimento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categoriaRequerimentos && comp.categoriaRequerimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
