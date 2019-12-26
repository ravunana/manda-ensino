import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';
import { RelacionamentoPessoaService } from './relacionamento-pessoa.service';

@Component({
  templateUrl: './relacionamento-pessoa-delete-dialog.component.html'
})
export class RelacionamentoPessoaDeleteDialogComponent {
  relacionamentoPessoa?: IRelacionamentoPessoa;

  constructor(
    protected relacionamentoPessoaService: RelacionamentoPessoaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relacionamentoPessoaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('relacionamentoPessoaListModification');
      this.activeModal.close();
    });
  }
}
