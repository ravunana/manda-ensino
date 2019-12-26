import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';

@Component({
  templateUrl: './lookup-delete-dialog.component.html'
})
export class LookupDeleteDialogComponent {
  lookup?: ILookup;

  constructor(protected lookupService: LookupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lookupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lookupListModification');
      this.activeModal.close();
    });
  }
}
