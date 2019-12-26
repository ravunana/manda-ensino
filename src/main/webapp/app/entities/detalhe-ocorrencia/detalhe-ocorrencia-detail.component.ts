import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';

@Component({
  selector: 'rv-detalhe-ocorrencia-detail',
  templateUrl: './detalhe-ocorrencia-detail.component.html'
})
export class DetalheOcorrenciaDetailComponent implements OnInit {
  detalheOcorrencia: IDetalheOcorrencia | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalheOcorrencia }) => {
      this.detalheOcorrencia = detalheOcorrencia;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
