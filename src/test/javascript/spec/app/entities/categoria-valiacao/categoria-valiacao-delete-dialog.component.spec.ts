import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EnsinoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CategoriaValiacaoDeleteDialogComponent } from 'app/entities/categoria-valiacao/categoria-valiacao-delete-dialog.component';
import { CategoriaValiacaoService } from 'app/entities/categoria-valiacao/categoria-valiacao.service';

describe('Component Tests', () => {
  describe('CategoriaValiacao Management Delete Component', () => {
    let comp: CategoriaValiacaoDeleteDialogComponent;
    let fixture: ComponentFixture<CategoriaValiacaoDeleteDialogComponent>;
    let service: CategoriaValiacaoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EnsinoTestModule],
        declarations: [CategoriaValiacaoDeleteDialogComponent]
      })
        .overrideTemplate(CategoriaValiacaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaValiacaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaValiacaoService);
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
