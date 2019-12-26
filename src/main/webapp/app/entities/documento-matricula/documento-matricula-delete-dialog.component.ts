import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentoMatricula } from 'app/shared/model/documento-matricula.model';
import { DocumentoMatriculaService } from './documento-matricula.service';

@Component({
  templateUrl: './documento-matricula-delete-dialog.component.html'
})
export class DocumentoMatriculaDeleteDialogComponent {
  documentoMatricula?: IDocumentoMatricula;

  constructor(
    protected documentoMatriculaService: DocumentoMatriculaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentoMatriculaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentoMatriculaListModification');
      this.activeModal.close();
    });
  }
}
