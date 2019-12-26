import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { ContactoInstituicaoEnsinoDetailComponent } from 'app/entities/contacto-instituicao-ensino/contacto-instituicao-ensino-detail.component';
import { ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

describe('Component Tests', () => {
  describe('ContactoInstituicaoEnsino Management Detail Component', () => {
    let comp: ContactoInstituicaoEnsinoDetailComponent;
    let fixture: ComponentFixture<ContactoInstituicaoEnsinoDetailComponent>;
    const route = ({ data: of({ contactoInstituicaoEnsino: new ContactoInstituicaoEnsino(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [ContactoInstituicaoEnsinoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContactoInstituicaoEnsinoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactoInstituicaoEnsinoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactoInstituicaoEnsino on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactoInstituicaoEnsino).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
