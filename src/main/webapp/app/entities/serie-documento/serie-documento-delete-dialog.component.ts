import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';

@Component({
  templateUrl: './serie-documento-delete-dialog.component.html'
})
export class SerieDocumentoDeleteDialogComponent {
  serieDocumento?: ISerieDocumento;

  constructor(
    protected serieDocumentoService: SerieDocumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serieDocumentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serieDocumentoListModification');
      this.activeModal.close();
    });
  }
}
