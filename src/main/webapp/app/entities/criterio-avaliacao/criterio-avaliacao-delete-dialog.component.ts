import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';
import { CriterioAvaliacaoService } from './criterio-avaliacao.service';

@Component({
  templateUrl: './criterio-avaliacao-delete-dialog.component.html'
})
export class CriterioAvaliacaoDeleteDialogComponent {
  criterioAvaliacao?: ICriterioAvaliacao;

  constructor(
    protected criterioAvaliacaoService: CriterioAvaliacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criterioAvaliacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('criterioAvaliacaoListModification');
      this.activeModal.close();
    });
  }
}
