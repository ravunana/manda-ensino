import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';
import { CategoriaRequerimentoService } from './categoria-requerimento.service';

@Component({
  templateUrl: './categoria-requerimento-delete-dialog.component.html'
})
export class CategoriaRequerimentoDeleteDialogComponent {
  categoriaRequerimento?: ICategoriaRequerimento;

  constructor(
    protected categoriaRequerimentoService: CategoriaRequerimentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaRequerimentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaRequerimentoListModification');
      this.activeModal.close();
    });
  }
}
