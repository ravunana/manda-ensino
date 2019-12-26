import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { CoordenadaBancariaComponent } from 'app/entities/coordenada-bancaria/coordenada-bancaria.component';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';
import { CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

describe('Component Tests', () => {
  describe('CoordenadaBancaria Management Component', () => {
    let comp: CoordenadaBancariaComponent;
    let fixture: ComponentFixture<CoordenadaBancariaComponent>;
    let service: CoordenadaBancariaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CoordenadaBancariaComponent],
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
        .overrideTemplate(CoordenadaBancariaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoordenadaBancariaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoordenadaBancariaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CoordenadaBancaria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coordenadaBancarias && comp.coordenadaBancarias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CoordenadaBancaria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coordenadaBancarias && comp.coordenadaBancarias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
