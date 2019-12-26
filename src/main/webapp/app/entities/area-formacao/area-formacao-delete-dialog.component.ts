import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAreaFormacao } from 'app/shared/model/area-formacao.model';
import { AreaFormacaoService } from './area-formacao.service';

@Component({
  templateUrl: './area-formacao-delete-dialog.component.html'
})
export class AreaFormacaoDeleteDialogComponent {
  areaFormacao?: IAreaFormacao;

  constructor(
    protected areaFormacaoService: AreaFormacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaFormacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('areaFormacaoListModification');
      this.activeModal.close();
    });
  }
}
