import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnsinoTestModule } from '../../../test.module';
import { SerieDocumentoDetailComponent } from 'app/entities/serie-documento/serie-documento-detail.component';
import { SerieDocumento } from 'app/shared/model/serie-documento.model';

describe('Component Tests', () => {
  describe('SerieDocumento Management Detail Component', () => {
    let comp: SerieDocumentoDetailComponent;
    let fixture: ComponentFixture<SerieDocumentoDetailComponent>;
    const route = ({ data: of({ serieDocumento: new SerieDocumento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [SerieDocumentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SerieDocumentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SerieDocumentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serieDocumento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serieDocumento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
