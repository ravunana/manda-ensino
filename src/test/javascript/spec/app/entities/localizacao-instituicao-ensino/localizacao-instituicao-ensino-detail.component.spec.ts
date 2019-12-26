import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { LocalizacaoInstituicaoEnsinoDetailComponent } from 'app/entities/localizacao-instituicao-ensino/localizacao-instituicao-ensino-detail.component';
import { LocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('LocalizacaoInstituicaoEnsino Management Detail Component', () => {
    let comp: LocalizacaoInstituicaoEnsinoDetailComponent;
    let fixture: ComponentFixture<LocalizacaoInstituicaoEnsinoDetailComponent>;
    const route = ({ data: of({ localizacaoInstituicaoEnsino: new LocalizacaoInstituicaoEnsino(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [LocalizacaoInstituicaoEnsinoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocalizacaoInstituicaoEnsinoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalizacaoInstituicaoEnsinoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load localizacaoInstituicaoEnsino on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localizacaoInstituicaoEnsino).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
