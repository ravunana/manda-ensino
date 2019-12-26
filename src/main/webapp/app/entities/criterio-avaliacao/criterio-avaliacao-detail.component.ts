import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';

@Component({
  selector: 'rv-criterio-avaliacao-detail',
  templateUrl: './criterio-avaliacao-detail.component.html'
})
export class CriterioAvaliacaoDetailComponent implements OnInit {
  criterioAvaliacao: ICriterioAvaliacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criterioAvaliacao }) => {
      this.criterioAvaliacao = criterioAvaliacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
