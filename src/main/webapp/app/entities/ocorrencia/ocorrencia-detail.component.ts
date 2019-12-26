import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOcorrencia } from 'app/shared/model/ocorrencia.model';

@Component({
  selector: 'rv-ocorrencia-detail',
  templateUrl: './ocorrencia-detail.component.html'
})
export class OcorrenciaDetailComponent implements OnInit {
  ocorrencia: IOcorrencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocorrencia }) => {
      this.ocorrencia = ocorrencia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
