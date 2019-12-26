import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';
import { UnidadeCurricularService } from './unidade-curricular.service';

@Component({
  templateUrl: './unidade-curricular-delete-dialog.component.html'
})
export class UnidadeCurricularDeleteDialogComponent {
  unidadeCurricular?: IUnidadeCurricular;

  constructor(
    protected unidadeCurricularService: UnidadeCurricularService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unidadeCurricularService.delete(id).subscribe(() => {
      this.eventManager.broadcast('unidadeCurricularListModification');
      this.activeModal.close();
    });
  }
}
