import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { DocumentacaoPessoaDetailComponent } from 'app/entities/documentacao-pessoa/documentacao-pessoa-detail.component';
import { DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

describe('Component Tests', () => {
  describe('DocumentacaoPessoa Management Detail Component', () => {
    let comp: DocumentacaoPessoaDetailComponent;
    let fixture: ComponentFixture<DocumentacaoPessoaDetailComponent>;
    const route = ({ data: of({ documentacaoPessoa: new DocumentacaoPessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [DocumentacaoPessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocumentacaoPessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentacaoPessoaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load documentacaoPessoa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentacaoPessoa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
