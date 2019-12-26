import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

@Component({
  selector: 'rv-localizacao-instituicao-ensino-detail',
  templateUrl: './localizacao-instituicao-ensino-detail.component.html'
})
export class LocalizacaoInstituicaoEnsinoDetailComponent implements OnInit {
  localizacaoInstituicaoEnsino: ILocalizacaoInstituicaoEnsino | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localizacaoInstituicaoEnsino }) => {
      this.localizacaoInstituicaoEnsino = localizacaoInstituicaoEnsino;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
