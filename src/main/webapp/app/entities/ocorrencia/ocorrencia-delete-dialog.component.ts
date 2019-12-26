import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOcorrencia } from 'app/shared/model/ocorrencia.model';
import { OcorrenciaService } from './ocorrencia.service';

@Component({
  templateUrl: './ocorrencia-delete-dialog.component.html'
})
export class OcorrenciaDeleteDialogComponent {
  ocorrencia?: IOcorrencia;

  constructor(
    protected ocorrenciaService: OcorrenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ocorrenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ocorrenciaListModification');
      this.activeModal.close();
    });
  }
}
