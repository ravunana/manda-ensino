import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { FormaLiquidacaoComponent } from 'app/entities/forma-liquidacao/forma-liquidacao.component';
import { FormaLiquidacaoService } from 'app/entities/forma-liquidacao/forma-liquidacao.service';
import { FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

describe('Component Tests', () => {
  describe('FormaLiquidacao Management Component', () => {
    let comp: FormaLiquidacaoComponent;
    let fixture: ComponentFixture<FormaLiquidacaoComponent>;
    let service: FormaLiquidacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [FormaLiquidacaoComponent],
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
        .overrideTemplate(FormaLiquidacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormaLiquidacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormaLiquidacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FormaLiquidacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.formaLiquidacaos && comp.formaLiquidacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FormaLiquidacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.formaLiquidacaos && comp.formaLiquidacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
