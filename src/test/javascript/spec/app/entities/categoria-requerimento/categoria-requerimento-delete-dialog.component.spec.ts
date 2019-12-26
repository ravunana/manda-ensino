import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CategoriaRequerimentoDeleteDialogComponent } from 'app/entities/categoria-requerimento/categoria-requerimento-delete-dialog.component';
import { CategoriaRequerimentoService } from 'app/entities/categoria-requerimento/categoria-requerimento.service';

describe('Component Tests', () => {
  describe('CategoriaRequerimento Management Delete Component', () => {
    let comp: CategoriaRequerimentoDeleteDialogComponent;
    let fixture: ComponentFixture<CategoriaRequerimentoDeleteDialogComponent>;
    let service: CategoriaRequerimentoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaRequerimentoDeleteDialogComponent]
      })
        .overrideTemplate(CategoriaRequerimentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaRequerimentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaRequerimentoService);
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
