import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DepositoDetailComponent } from 'app/entities/deposito/deposito-detail.component';
import { Deposito } from 'app/shared/model/deposito.model';

describe('Component Tests', () => {
  describe('Deposito Management Detail Component', () => {
    let comp: DepositoDetailComponent;
    let fixture: ComponentFixture<DepositoDetailComponent>;
    const route = ({ data: of({ deposito: new Deposito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DepositoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepositoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepositoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deposito on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deposito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
