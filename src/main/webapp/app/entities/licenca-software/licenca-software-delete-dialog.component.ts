import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';

@Component({
  templateUrl: './licenca-software-delete-dialog.component.html'
})
export class LicencaSoftwareDeleteDialogComponent {
  licencaSoftware?: ILicencaSoftware;

  constructor(
    protected licencaSoftwareService: LicencaSoftwareService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.licencaSoftwareService.delete(id).subscribe(() => {
      this.eventManager.broadcast('licencaSoftwareListModification');
      this.activeModal.close();
    });
  }
}
