import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRequerimento } from 'app/shared/model/requerimento.model';

@Component({
  selector: 'rv-requerimento-detail',
  templateUrl: './requerimento-detail.component.html'
})
export class RequerimentoDetailComponent implements OnInit {
  requerimento: IRequerimento | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requerimento }) => {
      this.requerimento = requerimento;
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
