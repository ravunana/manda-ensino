import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';

@Component({
  selector: 'rv-categoria-valiacao-detail',
  templateUrl: './categoria-valiacao-detail.component.html'
})
export class CategoriaValiacaoDetailComponent implements OnInit {
  categoriaValiacao: ICategoriaValiacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaValiacao }) => {
      this.categoriaValiacao = categoriaValiacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
