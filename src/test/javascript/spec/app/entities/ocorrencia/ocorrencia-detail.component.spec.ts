import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { OcorrenciaDetailComponent } from 'app/entities/ocorrencia/ocorrencia-detail.component';
import { Ocorrencia } from 'app/shared/model/ocorrencia.model';

describe('Component Tests', () => {
  describe('Ocorrencia Management Detail Component', () => {
    let comp: OcorrenciaDetailComponent;
    let fixture: ComponentFixture<OcorrenciaDetailComponent>;
    const route = ({ data: of({ ocorrencia: new Ocorrencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [OcorrenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OcorrenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OcorrenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ocorrencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ocorrencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
