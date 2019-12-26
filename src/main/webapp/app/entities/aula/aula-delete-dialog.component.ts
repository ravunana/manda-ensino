import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAula } from 'app/shared/model/aula.model';
import { AulaService } from './aula.service';

@Component({
  templateUrl: './aula-delete-dialog.component.html'
})
export class AulaDeleteDialogComponent {
  aula?: IAula;

  constructor(protected aulaService: AulaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aulaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aulaListModification');
      this.activeModal.close();
    });
  }
}
