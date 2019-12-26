import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { MatrizCurricularDetailComponent } from 'app/entities/matriz-curricular/matriz-curricular-detail.component';
import { MatrizCurricular } from 'app/shared/model/matriz-curricular.model';

describe('Component Tests', () => {
  describe('MatrizCurricular Management Detail Component', () => {
    let comp: MatrizCurricularDetailComponent;
    let fixture: ComponentFixture<MatrizCurricularDetailComponent>;
    const route = ({ data: of({ matrizCurricular: new MatrizCurricular(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [MatrizCurricularDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MatrizCurricularDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatrizCurricularDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matrizCurricular on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matrizCurricular).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
