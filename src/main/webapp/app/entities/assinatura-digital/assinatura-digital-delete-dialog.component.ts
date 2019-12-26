import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssinaturaDigital } from 'app/shared/model/assinatura-digital.model';
import { AssinaturaDigitalService } from './assinatura-digital.service';

@Component({
  templateUrl: './assinatura-digital-delete-dialog.component.html'
})
export class AssinaturaDigitalDeleteDialogComponent {
  assinaturaDigital?: IAssinaturaDigital;

  constructor(
    protected assinaturaDigitalService: AssinaturaDigitalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assinaturaDigitalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('assinaturaDigitalListModification');
      this.activeModal.close();
    });
  }
}
