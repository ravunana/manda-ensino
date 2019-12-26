import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISoftware } from 'app/shared/model/software.model';
import { SoftwareService } from './software.service';

@Component({
  templateUrl: './software-delete-dialog.component.html'
})
export class SoftwareDeleteDialogComponent {
  software?: ISoftware;

  constructor(protected softwareService: SoftwareService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.softwareService.delete(id).subscribe(() => {
      this.eventManager.broadcast('softwareListModification');
      this.activeModal.close();
    });
  }
}
