import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAreaFormacao, AreaFormacao } from 'app/shared/model/area-formacao.model';
import { AreaFormacaoService } from './area-formacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'rv-area-formacao-update',
  templateUrl: './area-formacao-update.component.html'
})
export class AreaFormacaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(10)]],
    competencias: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected areaFormacaoService: AreaFormacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaFormacao }) => {
      this.updateForm(areaFormacao);
    });
  }

  updateForm(areaFormacao: IAreaFormacao): void {
    this.editForm.patchValue({
      id: areaFormacao.id,
      nome: areaFormacao.nome,
      competencias: areaFormacao.competencias
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
    const areaFormacao = this.createFromForm();
    if (areaFormacao.id !== undefined) {
      this.subscribeToSaveResponse(this.areaFormacaoService.update(areaFormacao));
    } else {
      this.subscribeToSaveResponse(this.areaFormacaoService.create(areaFormacao));
    }
  }

  private createFromForm(): IAreaFormacao {
    return {
      ...new AreaFormacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      competencias: this.editForm.get(['competencias'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaFormacao>>): void {
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
}
