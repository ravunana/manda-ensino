import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { ContactoInstituicaoEnsinoComponent } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino.component';
import { ContactoInstituicaoEnsinoService } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino.service';
import { ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('ContactoInstituicaoEnsino Management Component', () => {
    let comp: ContactoInstituicaoEnsinoComponent;
    let fixture: ComponentFixture<ContactoInstituicaoEnsinoComponent>;
    let service: ContactoInstituicaoEnsinoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [ContactoInstituicaoEnsinoComponent],
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
        .overrideTemplate(ContactoInstituicaoEnsinoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactoInstituicaoEnsinoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactoInstituicaoEnsinoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactoInstituicaoEnsino(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactoInstituicaoEnsinos && comp.contactoInstituicaoEnsinos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactoInstituicaoEnsino(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactoInstituicaoEnsinos && comp.contactoInstituicaoEnsinos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
