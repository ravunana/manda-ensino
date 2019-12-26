import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISala, Sala } from 'app/shared/model/sala.model';
import { SalaService } from './sala.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'rv-sala-update',
  templateUrl: './sala-update.component.html'
})
export class SalaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required]],
    descricao: [],
    lotacao: [null, [Validators.required, Validators.min(30)]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected salaService: SalaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sala }) => {
      this.updateForm(sala);
    });
  }

  updateForm(sala: ISala): void {
    this.editForm.patchValue({
      id: sala.id,
      numero: sala.numero,
      descricao: sala.descricao,
      lotacao: sala.lotacao
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
    const sala = this.createFromForm();
    if (sala.id !== undefined) {
      this.subscribeToSaveResponse(this.salaService.update(sala));
    } else {
      this.subscribeToSaveResponse(this.salaService.create(sala));
    }
  }

  private createFromForm(): ISala {
    return {
      ...new Sala(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      lotacao: this.editForm.get(['lotacao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISala>>): void {
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
