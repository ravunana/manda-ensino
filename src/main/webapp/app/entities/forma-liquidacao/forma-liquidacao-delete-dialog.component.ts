import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from './forma-liquidacao.service';

@Component({
  templateUrl: './forma-liquidacao-delete-dialog.component.html'
})
export class FormaLiquidacaoDeleteDialogComponent {
  formaLiquidacao?: IFormaLiquidacao;

  constructor(
    protected formaLiquidacaoService: FormaLiquidacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formaLiquidacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formaLiquidacaoListModification');
      this.activeModal.close();
    });
  }
}
