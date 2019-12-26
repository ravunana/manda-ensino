import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatrizCurricular } from 'app/shared/model/matriz-curricular.model';
import { MatrizCurricularService } from './matriz-curricular.service';

@Component({
  templateUrl: './matriz-curricular-delete-dialog.component.html'
})
export class MatrizCurricularDeleteDialogComponent {
  matrizCurricular?: IMatrizCurricular;

  constructor(
    protected matrizCurricularService: MatrizCurricularService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matrizCurricularService.delete(id).subscribe(() => {
      this.eventManager.broadcast('matrizCurricularListModification');
      this.activeModal.close();
    });
  }
}
