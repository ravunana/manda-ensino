import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoActividade } from 'app/shared/model/plano-actividade.model';
import { PlanoActividadeService } from './plano-actividade.service';

@Component({
  templateUrl: './plano-actividade-delete-dialog.component.html'
})
export class PlanoActividadeDeleteDialogComponent {
  planoActividade?: IPlanoActividade;

  constructor(
    protected planoActividadeService: PlanoActividadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoActividadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoActividadeListModification');
      this.activeModal.close();
    });
  }
}
