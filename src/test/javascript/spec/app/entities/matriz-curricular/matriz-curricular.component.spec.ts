import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { MatrizCurricularComponent } from 'app/entities/matriz-curricular/matriz-curricular.component';
import { MatrizCurricularService } from 'app/entities/matriz-curricular/matriz-curricular.service';
import { MatrizCurricular } from 'app/shared/model/matriz-curricular.model';

describe('Component Tests', () => {
  describe('MatrizCurricular Management Component', () => {
    let comp: MatrizCurricularComponent;
    let fixture: ComponentFixture<MatrizCurricularComponent>;
    let service: MatrizCurricularService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [MatrizCurricularComponent],
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
        .overrideTemplate(MatrizCurricularComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatrizCurricularComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatrizCurricularService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MatrizCurricular(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.matrizCurriculars && comp.matrizCurriculars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MatrizCurricular(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.matrizCurriculars && comp.matrizCurriculars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
