import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { DetalheOcorrenciaComponent } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia.component';
import { DetalheOcorrenciaService } from 'app/entities/detalhe-ocorrencia/detalhe-ocorrencia.service';
import { DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

describe('Component Tests', () => {
  describe('DetalheOcorrencia Management Component', () => {
    let comp: DetalheOcorrenciaComponent;
    let fixture: ComponentFixture<DetalheOcorrenciaComponent>;
    let service: DetalheOcorrenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DetalheOcorrenciaComponent],
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
        .overrideTemplate(DetalheOcorrenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalheOcorrenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalheOcorrenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetalheOcorrencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detalheOcorrencias && comp.detalheOcorrencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetalheOcorrencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detalheOcorrencias && comp.detalheOcorrencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
