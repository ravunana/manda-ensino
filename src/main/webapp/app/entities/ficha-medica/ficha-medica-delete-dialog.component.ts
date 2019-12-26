import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichaMedica } from 'app/shared/model/ficha-medica.model';
import { FichaMedicaService } from './ficha-medica.service';

@Component({
  templateUrl: './ficha-medica-delete-dialog.component.html'
})
export class FichaMedicaDeleteDialogComponent {
  fichaMedica?: IFichaMedica;

  constructor(
    protected fichaMedicaService: FichaMedicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fichaMedicaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fichaMedicaListModification');
      this.activeModal.close();
    });
  }
}
