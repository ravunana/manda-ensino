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

import { IMatricula, Matricula } from 'app/shared/model/matricula.model';
import { MatriculaService } from './matricula.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { ICategoriaAluno } from 'app/shared/model/categoria-aluno.model';
import { CategoriaAlunoService } from 'app/entities/categoria-aluno/categoria-aluno.service';
import { ITurma } from 'app/shared/model/turma.model';
import { TurmaService } from 'app/entities/turma/turma.service';

type SelectableEntity = IUser | IAluno | IMatricula | ICategoriaAluno | ITurma;

@Component({
  selector: 'rv-matricula-update',
  templateUrl: './matricula-update.component.html'
})
export class MatriculaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  alunos: IAluno[] = [];

  matriculas: IMatricula[] = [];

  categoriaalunos: ICategoriaAluno[] = [];

  turmas: ITurma[] = [];

  editForm = this.fb.group({
    id: [],
    data: [],
    numero: [null, [Validators.required, Validators.min(1)]],
    observacao: [],
    anoLectivo: [null, [Validators.required]],
    peridoLectivo: [null, [Validators.required]],
    utilizadorId: [],
    alunoId: [null, Validators.required],
    confirmacaoId: [],
    categoriaId: [null, Validators.required],
    turmaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected matriculaService: MatriculaService,
    protected userService: UserService,
    protected alunoService: AlunoService,
    protected categoriaAlunoService: CategoriaAlunoService,
    protected turmaService: TurmaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricula }) => {
      this.updateForm(matricula);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));

      this.matriculaService
        .query()
        .pipe(
          map((res: HttpResponse<IMatricula[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMatricula[]) => (this.matriculas = resBody));

      this.categoriaAlunoService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoriaAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoriaAluno[]) => (this.categoriaalunos = resBody));

      this.turmaService
        .query()
        .pipe(
          map((res: HttpResponse<ITurma[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITurma[]) => (this.turmas = resBody));
    });
  }

  updateForm(matricula: IMatricula): void {
    this.editForm.patchValue({
      id: matricula.id,
      data: matricula.data != null ? matricula.data.format(DATE_TIME_FORMAT) : null,
      numero: matricula.numero,
      observacao: matricula.observacao,
      anoLectivo: matricula.anoLectivo,
      peridoLectivo: matricula.peridoLectivo,
      utilizadorId: matricula.utilizadorId,
      alunoId: matricula.alunoId,
      confirmacaoId: matricula.confirmacaoId,
      categoriaId: matricula.categoriaId,
      turmaId: matricula.turmaId
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
    const matricula = this.createFromForm();
    if (matricula.id !== undefined) {
      this.subscribeToSaveResponse(this.matriculaService.update(matricula));
    } else {
      this.subscribeToSaveResponse(this.matriculaService.create(matricula));
    }
  }

  private createFromForm(): IMatricula {
    return {
      ...new Matricula(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      numero: this.editForm.get(['numero'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      peridoLectivo: this.editForm.get(['peridoLectivo'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value,
      confirmacaoId: this.editForm.get(['confirmacaoId'])!.value,
      categoriaId: this.editForm.get(['categoriaId'])!.value,
      turmaId: this.editForm.get(['turmaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatricula>>): void {
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
