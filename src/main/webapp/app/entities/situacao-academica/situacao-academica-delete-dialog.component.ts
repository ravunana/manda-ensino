import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISituacaoAcademica } from 'app/shared/model/situacao-academica.model';
import { SituacaoAcademicaService } from './situacao-academica.service';

@Component({
  templateUrl: './situacao-academica-delete-dialog.component.html'
})
export class SituacaoAcademicaDeleteDialogComponent {
  situacaoAcademica?: ISituacaoAcademica;

  constructor(
    protected situacaoAcademicaService: SituacaoAcademicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.situacaoAcademicaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('situacaoAcademicaListModification');
      this.activeModal.close();
    });
  }
}
