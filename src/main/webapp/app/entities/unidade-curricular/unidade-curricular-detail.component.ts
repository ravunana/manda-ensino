import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';

@Component({
  selector: 'rv-unidade-curricular-detail',
  templateUrl: './unidade-curricular-detail.component.html'
})
export class UnidadeCurricularDetailComponent implements OnInit {
  unidadeCurricular: IUnidadeCurricular | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unidadeCurricular }) => {
      this.unidadeCurricular = unidadeCurricular;
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
