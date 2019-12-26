import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';
import { CategoriaValiacaoService } from './categoria-valiacao.service';

@Component({
  templateUrl: './categoria-valiacao-delete-dialog.component.html'
})
export class CategoriaValiacaoDeleteDialogComponent {
  categoriaValiacao?: ICategoriaValiacao;

  constructor(
    protected categoriaValiacaoService: CategoriaValiacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaValiacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaValiacaoListModification');
      this.activeModal.close();
    });
  }
}
