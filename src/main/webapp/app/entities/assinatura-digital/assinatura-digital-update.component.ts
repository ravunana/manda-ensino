import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAssinaturaDigital, AssinaturaDigital } from 'app/shared/model/assinatura-digital.model';
import { AssinaturaDigitalService } from './assinatura-digital.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

@Component({
  selector: 'rv-assinatura-digital-update',
  templateUrl: './assinatura-digital-update.component.html'
})
export class AssinaturaDigitalUpdateComponent implements OnInit {
  isSaving = false;

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    assinatura: [],
    assinaturaContentType: [],
    hashcode: [null, []],
    data: [],
    instituicaoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected assinaturaDigitalService: AssinaturaDigitalService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assinaturaDigital }) => {
      this.updateForm(assinaturaDigital);

      this.instituicaoEnsinoService
        .query()
        .pipe(
          map((res: HttpResponse<IInstituicaoEnsino[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInstituicaoEnsino[]) => (this.instituicaoensinos = resBody));
    });
  }

  updateForm(assinaturaDigital: IAssinaturaDigital): void {
    this.editForm.patchValue({
      id: assinaturaDigital.id,
      tipo: assinaturaDigital.tipo,
      assinatura: assinaturaDigital.assinatura,
      assinaturaContentType: assinaturaDigital.assinaturaContentType,
      hashcode: assinaturaDigital.hashcode,
      data: assinaturaDigital.data != null ? assinaturaDigital.data.format(DATE_TIME_FORMAT) : null,
      instituicaoId: assinaturaDigital.instituicaoId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ensinoApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assinaturaDigital = this.createFromForm();
    if (assinaturaDigital.id !== undefined) {
      this.subscribeToSaveResponse(this.assinaturaDigitalService.update(assinaturaDigital));
    } else {
      this.subscribeToSaveResponse(this.assinaturaDigitalService.create(assinaturaDigital));
    }
  }

  private createFromForm(): IAssinaturaDigital {
    return {
      ...new AssinaturaDigital(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      assinaturaContentType: this.editForm.get(['assinaturaContentType'])!.value,
      assinatura: this.editForm.get(['assinatura'])!.value,
      hashcode: this.editForm.get(['hashcode'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      instituicaoId: this.editForm.get(['instituicaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssinaturaDigital>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IInstituicaoEnsino): any {
    return item.id;
  }
}
