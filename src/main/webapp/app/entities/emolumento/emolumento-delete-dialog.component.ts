import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmolumento } from 'app/shared/model/emolumento.model';
import { EmolumentoService } from './emolumento.service';

@Component({
  templateUrl: './emolumento-delete-dialog.component.html'
})
export class EmolumentoDeleteDialogComponent {
  emolumento?: IEmolumento;

  constructor(
    protected emolumentoService: EmolumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emolumentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emolumentoListModification');
      this.activeModal.close();
    });
  }
}
