import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { CriterioAvaliacaoComponent } from 'app/entities/criterio-avaliacao/criterio-avaliacao.component';
import { CriterioAvaliacaoService } from 'app/entities/criterio-avaliacao/criterio-avaliacao.service';
import { CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

describe('Component Tests', () => {
  describe('CriterioAvaliacao Management Component', () => {
    let comp: CriterioAvaliacaoComponent;
    let fixture: ComponentFixture<CriterioAvaliacaoComponent>;
    let service: CriterioAvaliacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CriterioAvaliacaoComponent],
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
        .overrideTemplate(CriterioAvaliacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CriterioAvaliacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CriterioAvaliacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CriterioAvaliacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.criterioAvaliacaos && comp.criterioAvaliacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CriterioAvaliacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.criterioAvaliacaos && comp.criterioAvaliacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
