import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPlanoActividade } from 'app/shared/model/plano-actividade.model';

@Component({
  selector: 'rv-plano-actividade-detail',
  templateUrl: './plano-actividade-detail.component.html'
})
export class PlanoActividadeDetailComponent implements OnInit {
  planoActividade: IPlanoActividade | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoActividade }) => {
      this.planoActividade = planoActividade;
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
