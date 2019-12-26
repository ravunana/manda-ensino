import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContrato, Contrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';

@Component({
  selector: 'rv-contrato-update',
  templateUrl: './contrato-update.component.html'
})
export class ContratoUpdateComponent implements OnInit {
  isSaving = false;

  alunos: IAluno[] = [];
  deDp: any;
  ateDp: any;

  editForm = this.fb.group({
    id: [],
    de: [null, [Validators.required]],
    ate: [null, [Validators.required]],
    contrato: [null, [Validators.required]],
    aceitaTermos: [null, [Validators.required]],
    observacao: [],
    termos: [],
    termosContentType: [],
    emVigor: [null, [Validators.required]],
    alunoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contratoService: ContratoService,
    protected alunoService: AlunoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrato }) => {
      this.updateForm(contrato);

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));
    });
  }

  updateForm(contrato: IContrato): void {
    this.editForm.patchValue({
      id: contrato.id,
      de: contrato.de,
      ate: contrato.ate,
      contrato: contrato.contrato,
      aceitaTermos: contrato.aceitaTermos,
      observacao: contrato.observacao,
      termos: contrato.termos,
      termosContentType: contrato.termosContentType,
      emVigor: contrato.emVigor,
      alunoId: contrato.alunoId
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
    const contrato = this.createFromForm();
    if (contrato.id !== undefined) {
      this.subscribeToSaveResponse(this.contratoService.update(contrato));
    } else {
      this.subscribeToSaveResponse(this.contratoService.create(contrato));
    }
  }

  private createFromForm(): IContrato {
    return {
      ...new Contrato(),
      id: this.editForm.get(['id'])!.value,
      de: this.editForm.get(['de'])!.value,
      ate: this.editForm.get(['ate'])!.value,
      contrato: this.editForm.get(['contrato'])!.value,
      aceitaTermos: this.editForm.get(['aceitaTermos'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      termosContentType: this.editForm.get(['termosContentType'])!.value,
      termos: this.editForm.get(['termos'])!.value,
      emVigor: this.editForm.get(['emVigor'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrato>>): void {
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

  trackById(index: number, item: IAluno): any {
    return item.id;
  }
}
