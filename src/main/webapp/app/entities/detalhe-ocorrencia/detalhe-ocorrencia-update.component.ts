import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDetalheOcorrencia, DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';
import { DetalheOcorrenciaService } from './detalhe-ocorrencia.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IOcorrencia } from 'app/shared/model/ocorrencia.model';
import { OcorrenciaService } from 'app/entities/ocorrencia/ocorrencia.service';

@Component({
  selector: 'rv-detalhe-ocorrencia-update',
  templateUrl: './detalhe-ocorrencia-update.component.html'
})
export class DetalheOcorrenciaUpdateComponent implements OnInit {
  isSaving = false;

  ocorrencias: IOcorrencia[] = [];
  deDp: any;
  ateDp: any;

  editForm = this.fb.group({
    id: [],
    de: [],
    ate: [],
    motivo: [null, [Validators.required]],
    ocorrenciaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected detalheOcorrenciaService: DetalheOcorrenciaService,
    protected ocorrenciaService: OcorrenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalheOcorrencia }) => {
      this.updateForm(detalheOcorrencia);

      this.ocorrenciaService
        .query({ filter: 'detalheocorrencia-is-null' })
        .pipe(
          map((res: HttpResponse<IOcorrencia[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOcorrencia[]) => {
          if (!detalheOcorrencia.ocorrenciaId) {
            this.ocorrencias = resBody;
          } else {
            this.ocorrenciaService
              .find(detalheOcorrencia.ocorrenciaId)
              .pipe(
                map((subRes: HttpResponse<IOcorrencia>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOcorrencia[]) => {
                this.ocorrencias = concatRes;
              });
          }
        });
    });
  }

  updateForm(detalheOcorrencia: IDetalheOcorrencia): void {
    this.editForm.patchValue({
      id: detalheOcorrencia.id,
      de: detalheOcorrencia.de,
      ate: detalheOcorrencia.ate,
      motivo: detalheOcorrencia.motivo,
      ocorrenciaId: detalheOcorrencia.ocorrenciaId
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
    const detalheOcorrencia = this.createFromForm();
    if (detalheOcorrencia.id !== undefined) {
      this.subscribeToSaveResponse(this.detalheOcorrenciaService.update(detalheOcorrencia));
    } else {
      this.subscribeToSaveResponse(this.detalheOcorrenciaService.create(detalheOcorrencia));
    }
  }

  private createFromForm(): IDetalheOcorrencia {
    return {
      ...new DetalheOcorrencia(),
      id: this.editForm.get(['id'])!.value,
      de: this.editForm.get(['de'])!.value,
      ate: this.editForm.get(['ate'])!.value,
      motivo: this.editForm.get(['motivo'])!.value,
      ocorrenciaId: this.editForm.get(['ocorrenciaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalheOcorrencia>>): void {
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

  trackById(index: number, item: IOcorrencia): any {
    return item.id;
  }
}
