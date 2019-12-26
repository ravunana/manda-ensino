import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';
import { DocumentacaoPessoaService } from './documentacao-pessoa.service';

@Component({
  templateUrl: './documentacao-pessoa-delete-dialog.component.html'
})
export class DocumentacaoPessoaDeleteDialogComponent {
  documentacaoPessoa?: IDocumentacaoPessoa;

  constructor(
    protected documentacaoPessoaService: DocumentacaoPessoaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentacaoPessoaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentacaoPessoaListModification');
      this.activeModal.close();
    });
  }
}
