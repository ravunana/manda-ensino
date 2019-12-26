import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategoriaAluno } from 'app/shared/model/categoria-aluno.model';

@Component({
  selector: 'rv-categoria-aluno-detail',
  templateUrl: './categoria-aluno-detail.component.html'
})
export class CategoriaAlunoDetailComponent implements OnInit {
  categoriaAluno: ICategoriaAluno | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaAluno }) => {
      this.categoriaAluno = categoriaAluno;
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
