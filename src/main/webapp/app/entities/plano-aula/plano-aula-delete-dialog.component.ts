import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoAula } from 'app/shared/model/plano-aula.model';
import { PlanoAulaService } from './plano-aula.service';

@Component({
  templateUrl: './plano-aula-delete-dialog.component.html'
})
export class PlanoAulaDeleteDialogComponent {
  planoAula?: IPlanoAula;

  constructor(protected planoAulaService: PlanoAulaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoAulaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoAulaListModification');
      this.activeModal.close();
    });
  }
}
