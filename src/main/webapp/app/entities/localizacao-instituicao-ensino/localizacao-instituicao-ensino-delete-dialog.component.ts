import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';
import { LocalizacaoInstituicaoEnsinoService } from './localizacao-instituicao-ensino.service';

@Component({
  templateUrl: './localizacao-instituicao-ensino-delete-dialog.component.html'
})
export class LocalizacaoInstituicaoEnsinoDeleteDialogComponent {
  localizacaoInstituicaoEnsino?: ILocalizacaoInstituicaoEnsino;

  constructor(
    protected localizacaoInstituicaoEnsinoService: LocalizacaoInstituicaoEnsinoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.localizacaoInstituicaoEnsinoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('localizacaoInstituicaoEnsinoListModification');
      this.activeModal.close();
    });
  }
}
