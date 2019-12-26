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

import { ISituacaoAcademica, SituacaoAcademica } from 'app/shared/model/situacao-academica.model';
import { SituacaoAcademicaService } from './situacao-academica.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';

type SelectableEntity = IAluno | IDisciplina;

@Component({
  selector: 'rv-situacao-academica-update',
  templateUrl: './situacao-academica-update.component.html'
})
export class SituacaoAcademicaUpdateComponent implements OnInit {
  isSaving = false;

  alunos: IAluno[] = [];

  disciplinas: IDisciplina[] = [];

  editForm = this.fb.group({
    id: [],
    anoLectivo: [],
    data: [],
    estado: [],
    descricao: [],
    alunoId: [],
    disciplinaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected situacaoAcademicaService: SituacaoAcademicaService,
    protected alunoService: AlunoService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situacaoAcademica }) => {
      this.updateForm(situacaoAcademica);

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));

      this.disciplinaService
        .query()
        .pipe(
          map((res: HttpResponse<IDisciplina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDisciplina[]) => (this.disciplinas = resBody));
    });
  }

  updateForm(situacaoAcademica: ISituacaoAcademica): void {
    this.editForm.patchValue({
      id: situacaoAcademica.id,
      anoLectivo: situacaoAcademica.anoLectivo,
      data: situacaoAcademica.data != null ? situacaoAcademica.data.format(DATE_TIME_FORMAT) : null,
      estado: situacaoAcademica.estado,
      descricao: situacaoAcademica.descricao,
      alunoId: situacaoAcademica.alunoId,
      disciplinaId: situacaoAcademica.disciplinaId
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
    const situacaoAcademica = this.createFromForm();
    if (situacaoAcademica.id !== undefined) {
      this.subscribeToSaveResponse(this.situacaoAcademicaService.update(situacaoAcademica));
    } else {
      this.subscribeToSaveResponse(this.situacaoAcademicaService.create(situacaoAcademica));
    }
  }

  private createFromForm(): ISituacaoAcademica {
    return {
      ...new SituacaoAcademica(),
      id: this.editForm.get(['id'])!.value,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituacaoAcademica>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
