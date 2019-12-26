import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { PlanoCurricularService } from './plano-curricular.service';

@Component({
  templateUrl: './plano-curricular-delete-dialog.component.html'
})
export class PlanoCurricularDeleteDialogComponent {
  planoCurricular?: IPlanoCurricular;

  constructor(
    protected planoCurricularService: PlanoCurricularService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoCurricularService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoCurricularListModification');
      this.activeModal.close();
    });
  }
}
