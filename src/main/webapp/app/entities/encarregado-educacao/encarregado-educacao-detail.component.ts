import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';

@Component({
  selector: 'rv-encarregado-educacao-detail',
  templateUrl: './encarregado-educacao-detail.component.html'
})
export class EncarregadoEducacaoDetailComponent implements OnInit {
  encarregadoEducacao: IEncarregadoEducacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encarregadoEducacao }) => {
      this.encarregadoEducacao = encarregadoEducacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
