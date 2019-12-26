import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

@Component({
  selector: 'rv-instituicao-ensino-detail',
  templateUrl: './instituicao-ensino-detail.component.html'
})
export class InstituicaoEnsinoDetailComponent implements OnInit {
  instituicaoEnsino: IInstituicaoEnsino | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicaoEnsino }) => {
      this.instituicaoEnsino = instituicaoEnsino;
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
