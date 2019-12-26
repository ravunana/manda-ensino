import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';

@Component({
  selector: 'rv-categoria-requerimento-detail',
  templateUrl: './categoria-requerimento-detail.component.html'
})
export class CategoriaRequerimentoDetailComponent implements OnInit {
  categoriaRequerimento: ICategoriaRequerimento | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaRequerimento }) => {
      this.categoriaRequerimento = categoriaRequerimento;
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
