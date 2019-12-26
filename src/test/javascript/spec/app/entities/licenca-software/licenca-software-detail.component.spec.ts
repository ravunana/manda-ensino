import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { LicencaSoftwareDetailComponent } from 'app/entities/licenca-software/licenca-software-detail.component';
import { LicencaSoftware } from 'app/shared/model/licenca-software.model';

describe('Component Tests', () => {
  describe('LicencaSoftware Management Detail Component', () => {
    let comp: LicencaSoftwareDetailComponent;
    let fixture: ComponentFixture<LicencaSoftwareDetailComponent>;
    const route = ({ data: of({ licencaSoftware: new LicencaSoftware(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [LicencaSoftwareDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LicencaSoftwareDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LicencaSoftwareDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load licencaSoftware on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.licencaSoftware).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
