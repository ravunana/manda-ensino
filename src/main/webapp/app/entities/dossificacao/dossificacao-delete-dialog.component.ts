import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDossificacao } from 'app/shared/model/dossificacao.model';
import { DossificacaoService } from './dossificacao.service';

@Component({
  templateUrl: './dossificacao-delete-dialog.component.html'
})
export class DossificacaoDeleteDialogComponent {
  dossificacao?: IDossificacao;

  constructor(
    protected dossificacaoService: DossificacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dossificacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dossificacaoListModification');
      this.activeModal.close();
    });
  }
}
