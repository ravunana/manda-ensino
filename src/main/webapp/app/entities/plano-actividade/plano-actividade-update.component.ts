import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPlanoActividade, PlanoActividade } from 'app/shared/model/plano-actividade.model';
import { PlanoActividadeService } from './plano-actividade.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-plano-actividade-update',
  templateUrl: './plano-actividade-update.component.html'
})
export class PlanoActividadeUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];
  deDp: any;
  ateDp: any;
  anoLectivoDp: any;

  editForm = this.fb.group({
    id: [],
    numeroActividade: [null, [Validators.min(1)]],
    atividade: [null, [Validators.required]],
    objectivos: [null, [Validators.required]],
    de: [null, [Validators.required]],
    ate: [null, [Validators.required]],
    responsavel: [null, [Validators.required]],
    local: [],
    observacao: [],
    participantes: [],
    coResponsavel: [],
    statusActividade: [null, [Validators.required]],
    anoLectivo: [null, [Validators.required]],
    periodoLectivo: [],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected planoActividadeService: PlanoActividadeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoActividade }) => {
      this.updateForm(planoActividade);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(planoActividade: IPlanoActividade): void {
    this.editForm.patchValue({
      id: planoActividade.id,
      numeroActividade: planoActividade.numeroActividade,
      atividade: planoActividade.atividade,
      objectivos: planoActividade.objectivos,
      de: planoActividade.de,
      ate: planoActividade.ate,
      responsavel: planoActividade.responsavel,
      local: planoActividade.local,
      observacao: planoActividade.observacao,
      participantes: planoActividade.participantes,
      coResponsavel: planoActividade.coResponsavel,
      statusActividade: planoActividade.statusActividade,
      anoLectivo: planoActividade.anoLectivo,
      periodoLectivo: planoActividade.periodoLectivo,
      utilizadorId: planoActividade.utilizadorId
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
    const planoActividade = this.createFromForm();
    if (planoActividade.id !== undefined) {
      this.subscribeToSaveResponse(this.planoActividadeService.update(planoActividade));
    } else {
      this.subscribeToSaveResponse(this.planoActividadeService.create(planoActividade));
    }
  }

  private createFromForm(): IPlanoActividade {
    return {
      ...new PlanoActividade(),
      id: this.editForm.get(['id'])!.value,
      numeroActividade: this.editForm.get(['numeroActividade'])!.value,
      atividade: this.editForm.get(['atividade'])!.value,
      objectivos: this.editForm.get(['objectivos'])!.value,
      de: this.editForm.get(['de'])!.value,
      ate: this.editForm.get(['ate'])!.value,
      responsavel: this.editForm.get(['responsavel'])!.value,
      local: this.editForm.get(['local'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      participantes: this.editForm.get(['participantes'])!.value,
      coResponsavel: this.editForm.get(['coResponsavel'])!.value,
      statusActividade: this.editForm.get(['statusActividade'])!.value,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      periodoLectivo: this.editForm.get(['periodoLectivo'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoActividade>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
