import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { AssinaturaDigitalComponent } from 'app/entities/assinatura-digital/assinatura-digital.component';
import { AssinaturaDigitalService } from 'app/entities/assinatura-digital/assinatura-digital.service';
import { AssinaturaDigital } from 'app/shared/model/assinatura-digital.model';

describe('Component Tests', () => {
  describe('AssinaturaDigital Management Component', () => {
    let comp: AssinaturaDigitalComponent;
    let fixture: ComponentFixture<AssinaturaDigitalComponent>;
    let service: AssinaturaDigitalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [AssinaturaDigitalComponent],
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
        .overrideTemplate(AssinaturaDigitalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssinaturaDigitalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssinaturaDigitalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AssinaturaDigital(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.assinaturaDigitals && comp.assinaturaDigitals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AssinaturaDigital(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.assinaturaDigitals && comp.assinaturaDigitals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
