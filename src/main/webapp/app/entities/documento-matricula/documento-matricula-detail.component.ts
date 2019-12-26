import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentoMatricula } from 'app/shared/model/documento-matricula.model';

@Component({
  selector: 'rv-documento-matricula-detail',
  templateUrl: './documento-matricula-detail.component.html'
})
export class DocumentoMatriculaDetailComponent implements OnInit {
  documentoMatricula: IDocumentoMatricula | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentoMatricula }) => {
      this.documentoMatricula = documentoMatricula;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
