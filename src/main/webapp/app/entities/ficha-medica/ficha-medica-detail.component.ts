import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFichaMedica } from 'app/shared/model/ficha-medica.model';

@Component({
  selector: 'rv-ficha-medica-detail',
  templateUrl: './ficha-medica-detail.component.html'
})
export class FichaMedicaDetailComponent implements OnInit {
  fichaMedica: IFichaMedica | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fichaMedica }) => {
      this.fichaMedica = fichaMedica;
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
