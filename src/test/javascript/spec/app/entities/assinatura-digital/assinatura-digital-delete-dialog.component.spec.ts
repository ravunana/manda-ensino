import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AssinaturaDigitalDeleteDialogComponent } from 'app/entities/assinatura-digital/assinatura-digital-delete-dialog.component';
import { AssinaturaDigitalService } from 'app/entities/assinatura-digital/assinatura-digital.service';

describe('Component Tests', () => {
  describe('AssinaturaDigital Management Delete Component', () => {
    let comp: AssinaturaDigitalDeleteDialogComponent;
    let fixture: ComponentFixture<AssinaturaDigitalDeleteDialogComponent>;
    let service: AssinaturaDigitalService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [AssinaturaDigitalDeleteDialogComponent]
      })
        .overrideTemplate(AssinaturaDigitalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssinaturaDigitalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssinaturaDigitalService);
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
