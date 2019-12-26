import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EnsinoTestModule } from '../../../test.module';
import { LocalizacaoInstituicaoEnsinoComponent } from 'app/entities/localizacao-instituicao-ensino/localizacao-instituicao-ensino.component';
import { LocalizacaoInstituicaoEnsinoService } from 'app/entities/localizacao-instituicao-ensino/localizacao-instituicao-ensino.service';
import { LocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('LocalizacaoInstituicaoEnsino Management Component', () => {
    let comp: LocalizacaoInstituicaoEnsinoComponent;
    let fixture: ComponentFixture<LocalizacaoInstituicaoEnsinoComponent>;
    let service: LocalizacaoInstituicaoEnsinoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [LocalizacaoInstituicaoEnsinoComponent],
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
        .overrideTemplate(LocalizacaoInstituicaoEnsinoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalizacaoInstituicaoEnsinoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalizacaoInstituicaoEnsinoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LocalizacaoInstituicaoEnsino(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.localizacaoInstituicaoEnsinos && comp.localizacaoInstituicaoEnsinos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LocalizacaoInstituicaoEnsino(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.localizacaoInstituicaoEnsinos && comp.localizacaoInstituicaoEnsinos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
