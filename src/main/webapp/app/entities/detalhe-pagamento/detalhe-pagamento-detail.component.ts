import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

@Component({
  selector: 'rv-detalhe-pagamento-detail',
  templateUrl: './detalhe-pagamento-detail.component.html'
})
export class DetalhePagamentoDetailComponent implements OnInit {
  detalhePagamento: IDetalhePagamento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalhePagamento }) => {
      this.detalhePagamento = detalhePagamento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
