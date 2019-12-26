import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INota, Nota } from 'app/shared/model/nota.model';
import { NotaService } from './nota.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';
import { ITurma } from 'app/shared/model/turma.model';
import { TurmaService } from 'app/entities/turma/turma.service';
import { ICategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';
import { CategoriaValiacaoService } from 'app/entities/categoria-valiacao/categoria-valiacao.service';
import { IMatricula } from 'app/shared/model/matricula.model';
import { MatriculaService } from 'app/entities/matricula/matricula.service';

type SelectableEntity = IUser | IDisciplina | ITurma | ICategoriaValiacao | IMatricula;

@Component({
  selector: 'rv-nota-update',
  templateUrl: './nota-update.component.html'
})
export class NotaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  disciplinas: IDisciplina[] = [];

  turmas: ITurma[] = [];

  categoriavaliacaos: ICategoriaValiacao[] = [];

  matriculas: IMatricula[] = [];
  anoLectivoDp: any;

  editForm = this.fb.group({
    id: [],
    valor: [null, [Validators.required, Validators.min(0), Validators.max(20)]],
    data: [],
    anoLectivo: [],
    periodoLectivo: [],
    utilizadorId: [],
    disciplinaId: [null, Validators.required],
    turmaId: [null, Validators.required],
    categoriaAvaliacaoId: [],
    matriculaId: [null, Validators.required]
  });

  constructor(
    protected notaService: NotaService,
    protected userService: UserService,
    protected disciplinaService: DisciplinaService,
    protected turmaService: TurmaService,
    protected categoriaValiacaoService: CategoriaValiacaoService,
    protected matriculaService: MatriculaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nota }) => {
      this.updateForm(nota);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.disciplinaService
        .query()
        .pipe(
          map((res: HttpResponse<IDisciplina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDisciplina[]) => (this.disciplinas = resBody));

      this.turmaService
        .query()
        .pipe(
          map((res: HttpResponse<ITurma[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITurma[]) => (this.turmas = resBody));

      this.categoriaValiacaoService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoriaValiacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoriaValiacao[]) => (this.categoriavaliacaos = resBody));

      this.matriculaService
        .query()
        .pipe(
          map((res: HttpResponse<IMatricula[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMatricula[]) => (this.matriculas = resBody));
    });
  }

  updateForm(nota: INota): void {
    this.editForm.patchValue({
      id: nota.id,
      valor: nota.valor,
      data: nota.data != null ? nota.data.format(DATE_TIME_FORMAT) : null,
      anoLectivo: nota.anoLectivo,
      periodoLectivo: nota.periodoLectivo,
      utilizadorId: nota.utilizadorId,
      disciplinaId: nota.disciplinaId,
      turmaId: nota.turmaId,
      categoriaAvaliacaoId: nota.categoriaAvaliacaoId,
      matriculaId: nota.matriculaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nota = this.createFromForm();
    if (nota.id !== undefined) {
      this.subscribeToSaveResponse(this.notaService.update(nota));
    } else {
      this.subscribeToSaveResponse(this.notaService.create(nota));
    }
  }

  private createFromForm(): INota {
    return {
      ...new Nota(),
      id: this.editForm.get(['id'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      periodoLectivo: this.editForm.get(['periodoLectivo'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value,
      turmaId: this.editForm.get(['turmaId'])!.value,
      categoriaAvaliacaoId: this.editForm.get(['categoriaAvaliacaoId'])!.value,
      matriculaId: this.editForm.get(['matriculaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INota>>): void {
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
