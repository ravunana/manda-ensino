import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISerieDocumento } from 'app/shared/model/serie-documento.model';

@Component({
  selector: 'rv-serie-documento-detail',
  templateUrl: './serie-documento-detail.component.html'
})
export class SerieDocumentoDetailComponent implements OnInit {
  serieDocumento: ISerieDocumento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serieDocumento }) => {
      this.serieDocumento = serieDocumento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
