import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategoriaRequerimento, CategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';
import { CategoriaRequerimentoService } from './categoria-requerimento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'rv-categoria-requerimento-update',
  templateUrl: './categoria-requerimento-update.component.html'
})
export class CategoriaRequerimentoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
    tempoResposta: [],
    pagase: [],
    descricao: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categoriaRequerimentoService: CategoriaRequerimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaRequerimento }) => {
      this.updateForm(categoriaRequerimento);
    });
  }

  updateForm(categoriaRequerimento: ICategoriaRequerimento): void {
    this.editForm.patchValue({
      id: categoriaRequerimento.id,
      nome: categoriaRequerimento.nome,
      tempoResposta: categoriaRequerimento.tempoResposta,
      pagase: categoriaRequerimento.pagase,
      descricao: categoriaRequerimento.descricao
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
    const categoriaRequerimento = this.createFromForm();
    if (categoriaRequerimento.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaRequerimentoService.update(categoriaRequerimento));
    } else {
      this.subscribeToSaveResponse(this.categoriaRequerimentoService.create(categoriaRequerimento));
    }
  }

  private createFromForm(): ICategoriaRequerimento {
    return {
      ...new CategoriaRequerimento(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      tempoResposta: this.editForm.get(['tempoResposta'])!.value,
      pagase: this.editForm.get(['pagase'])!.value,
      descricao: this.editForm.get(['descricao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaRequerimento>>): void {
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
