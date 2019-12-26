import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

@Component({
  selector: 'rv-documentacao-pessoa-detail',
  templateUrl: './documentacao-pessoa-detail.component.html'
})
export class DocumentacaoPessoaDetailComponent implements OnInit {
  documentacaoPessoa: IDocumentacaoPessoa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentacaoPessoa }) => {
      this.documentacaoPessoa = documentacaoPessoa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
