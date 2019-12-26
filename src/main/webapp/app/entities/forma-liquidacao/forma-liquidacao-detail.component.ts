import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';

@Component({
  selector: 'rv-forma-liquidacao-detail',
  templateUrl: './forma-liquidacao-detail.component.html'
})
export class FormaLiquidacaoDetailComponent implements OnInit {
  formaLiquidacao: IFormaLiquidacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formaLiquidacao }) => {
      this.formaLiquidacao = formaLiquidacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
