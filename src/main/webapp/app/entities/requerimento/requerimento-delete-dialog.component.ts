import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequerimento } from 'app/shared/model/requerimento.model';
import { RequerimentoService } from './requerimento.service';

@Component({
  templateUrl: './requerimento-delete-dialog.component.html'
})
export class RequerimentoDeleteDialogComponent {
  requerimento?: IRequerimento;

  constructor(
    protected requerimentoService: RequerimentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requerimentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('requerimentoListModification');
      this.activeModal.close();
    });
  }
}
