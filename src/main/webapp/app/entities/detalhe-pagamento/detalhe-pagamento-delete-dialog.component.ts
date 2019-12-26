import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';
import { DetalhePagamentoService } from './detalhe-pagamento.service';

@Component({
  templateUrl: './detalhe-pagamento-delete-dialog.component.html'
})
export class DetalhePagamentoDeleteDialogComponent {
  detalhePagamento?: IDetalhePagamento;

  constructor(
    protected detalhePagamentoService: DetalhePagamentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalhePagamentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('detalhePagamentoListModification');
      this.activeModal.close();
    });
  }
}
