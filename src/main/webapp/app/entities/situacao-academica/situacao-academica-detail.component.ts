import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISituacaoAcademica } from 'app/shared/model/situacao-academica.model';

@Component({
  selector: 'rv-situacao-academica-detail',
  templateUrl: './situacao-academica-detail.component.html'
})
export class SituacaoAcademicaDetailComponent implements OnInit {
  situacaoAcademica: ISituacaoAcademica | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situacaoAcademica }) => {
      this.situacaoAcademica = situacaoAcademica;
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
