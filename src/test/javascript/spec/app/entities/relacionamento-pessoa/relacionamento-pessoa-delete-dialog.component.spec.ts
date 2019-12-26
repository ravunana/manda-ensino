import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RelacionamentoPessoaDeleteDialogComponent } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa-delete-dialog.component';
import { RelacionamentoPessoaService } from 'app/entities/relacionamento-pessoa/relacionamento-pessoa.service';

describe('Component Tests', () => {
  describe('RelacionamentoPessoa Management Delete Component', () => {
    let comp: RelacionamentoPessoaDeleteDialogComponent;
    let fixture: ComponentFixture<RelacionamentoPessoaDeleteDialogComponent>;
    let service: RelacionamentoPessoaService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [RelacionamentoPessoaDeleteDialogComponent]
      })
        .overrideTemplate(RelacionamentoPessoaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelacionamentoPessoaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelacionamentoPessoaService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
