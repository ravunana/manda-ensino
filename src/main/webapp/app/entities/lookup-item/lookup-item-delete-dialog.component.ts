import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILookupItem } from 'app/shared/model/lookup-item.model';
import { LookupItemService } from './lookup-item.service';

@Component({
  templateUrl: './lookup-item-delete-dialog.component.html'
})
export class LookupItemDeleteDialogComponent {
  lookupItem?: ILookupItem;

  constructor(
    protected lookupItemService: LookupItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lookupItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lookupItemListModification');
      this.activeModal.close();
    });
  }
}
