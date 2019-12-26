import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { ContactoPessoaService } from './contacto-pessoa.service';

@Component({
  templateUrl: './contacto-pessoa-delete-dialog.component.html'
})
export class ContactoPessoaDeleteDialogComponent {
  contactoPessoa?: IContactoPessoa;

  constructor(
    protected contactoPessoaService: ContactoPessoaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactoPessoaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactoPessoaListModification');
      this.activeModal.close();
    });
  }
}
