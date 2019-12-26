import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';
import { ContactoInstituicaoEnsinoService } from './contacto-instituicao-ensino.service';

@Component({
  templateUrl: './contacto-instituicao-ensino-delete-dialog.component.html'
})
export class ContactoInstituicaoEnsinoDeleteDialogComponent {
  contactoInstituicaoEnsino?: IContactoInstituicaoEnsino;

  constructor(
    protected contactoInstituicaoEnsinoService: ContactoInstituicaoEnsinoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactoInstituicaoEnsinoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactoInstituicaoEnsinoListModification');
      this.activeModal.close();
    });
  }
}
