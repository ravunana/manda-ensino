import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { MoradaPessoaComponent } from 'app/entities/morada-pessoa/morada-pessoa.component';
import { MoradaPessoaService } from 'app/entities/morada-pessoa/morada-pessoa.service';
import { MoradaPessoa } from 'app/shared/model/morada-pessoa.model';

describe('Component Tests', () => {
  describe('MoradaPessoa Management Component', () => {
    let comp: MoradaPessoaComponent;
    let fixture: ComponentFixture<MoradaPessoaComponent>;
    let service: MoradaPessoaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [MoradaPessoaComponent],
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
        .overrideTemplate(MoradaPessoaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MoradaPessoaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MoradaPessoaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MoradaPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.moradaPessoas && comp.moradaPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MoradaPessoa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.moradaPessoas && comp.moradaPessoas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
