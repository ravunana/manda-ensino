import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { PlanoCurricularDetailComponent } from 'app/entities/plano-curricular/plano-curricular-detail.component';
import { PlanoCurricular } from 'app/shared/model/plano-curricular.model';

describe('Component Tests', () => {
  describe('PlanoCurricular Management Detail Component', () => {
    let comp: PlanoCurricularDetailComponent;
    let fixture: ComponentFixture<PlanoCurricularDetailComponent>;
    const route = ({ data: of({ planoCurricular: new PlanoCurricular(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [PlanoCurricularDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlanoCurricularDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoCurricularDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoCurricular on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoCurricular).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
