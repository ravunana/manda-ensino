import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from './instituicao-ensino.service';

@Component({
  templateUrl: './instituicao-ensino-delete-dialog.component.html'
})
export class InstituicaoEnsinoDeleteDialogComponent {
  instituicaoEnsino?: IInstituicaoEnsino;

  constructor(
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instituicaoEnsinoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instituicaoEnsinoListModification');
      this.activeModal.close();
    });
  }
}
