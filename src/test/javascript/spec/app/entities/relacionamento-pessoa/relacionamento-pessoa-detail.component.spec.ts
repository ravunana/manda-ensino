import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { RelacionamentoPessoaDetailComponent } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa-detail.component';
import { RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

describe('Component Tests', () => {
  describe('RelacionamentoPessoa Management Detail Component', () => {
    let comp: RelacionamentoPessoaDetailComponent;
    let fixture: ComponentFixture<RelacionamentoPessoaDetailComponent>;
    const route = ({ data: of({ relacionamentoPessoa: new RelacionamentoPessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [RelacionamentoPessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RelacionamentoPessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelacionamentoPessoaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load relacionamentoPessoa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.relacionamentoPessoa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
