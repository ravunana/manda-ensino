import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';
import { DetalheOcorrenciaService } from './detalhe-ocorrencia.service';

@Component({
  templateUrl: './detalhe-ocorrencia-delete-dialog.component.html'
})
export class DetalheOcorrenciaDeleteDialogComponent {
  detalheOcorrencia?: IDetalheOcorrencia;

  constructor(
    protected detalheOcorrenciaService: DetalheOcorrenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalheOcorrenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('detalheOcorrenciaListModification');
      this.activeModal.close();
    });
  }
}
