import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { ContactoPessoaDetailComponent } from 'app/entities/contacto-pessoa/contacto-pessoa-detail.component';
import { ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

describe('Component Tests', () => {
  describe('ContactoPessoa Management Detail Component', () => {
    let comp: ContactoPessoaDetailComponent;
    let fixture: ComponentFixture<ContactoPessoaDetailComponent>;
    const route = ({ data: of({ contactoPessoa: new ContactoPessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [ContactoPessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContactoPessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactoPessoaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactoPessoa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactoPessoa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
