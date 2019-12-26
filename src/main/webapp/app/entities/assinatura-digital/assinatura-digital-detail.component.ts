import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAssinaturaDigital } from 'app/shared/model/assinatura-digital.model';

@Component({
  selector: 'rv-assinatura-digital-detail',
  templateUrl: './assinatura-digital-detail.component.html'
})
export class AssinaturaDigitalDetailComponent implements OnInit {
  assinaturaDigital: IAssinaturaDigital | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assinaturaDigital }) => {
      this.assinaturaDigital = assinaturaDigital;
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
