import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';
import { EncarregadoEducacaoService } from './encarregado-educacao.service';

@Component({
  templateUrl: './encarregado-educacao-delete-dialog.component.html'
})
export class EncarregadoEducacaoDeleteDialogComponent {
  encarregadoEducacao?: IEncarregadoEducacao;

  constructor(
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encarregadoEducacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encarregadoEducacaoListModification');
      this.activeModal.close();
    });
  }
}
