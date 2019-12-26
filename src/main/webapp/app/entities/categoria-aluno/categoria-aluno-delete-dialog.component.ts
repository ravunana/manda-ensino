import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaAluno } from 'app/shared/model/categoria-aluno.model';
import { CategoriaAlunoService } from './categoria-aluno.service';

@Component({
  templateUrl: './categoria-aluno-delete-dialog.component.html'
})
export class CategoriaAlunoDeleteDialogComponent {
  categoriaAluno?: ICategoriaAluno;

  constructor(
    protected categoriaAlunoService: CategoriaAlunoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaAlunoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaAlunoListModification');
      this.activeModal.close();
    });
  }
}
