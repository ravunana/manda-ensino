import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHorario, Horario } from 'app/shared/model/horario.model';
import { HorarioService } from './horario.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITurma } from 'app/shared/model/turma.model';
import { TurmaService } from 'app/entities/turma/turma.service';
import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from 'app/entities/professor/professor.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';

type SelectableEntity = IUser | ITurma | IProfessor | IDisciplina;

@Component({
  selector: 'rv-horario-update',
  templateUrl: './horario-update.component.html'
})
export class HorarioUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  turmas: ITurma[] = [];

  professors: IProfessor[] = [];

  disciplinas: IDisciplina[] = [];
  anoLectivoDp: any;

  editForm = this.fb.group({
    id: [],
    inicioAula: [null, [Validators.required]],
    terminoAlua: [null, [Validators.required]],
    intervalo: [null, [Validators.required]],
    diaSemana: [null, [Validators.required]],
    regimeCurricular: [null, [Validators.required]],
    data: [null, [Validators.required]],
    anoLectivo: [],
    categoria: [],
    utilizadorId: [],
    turmaId: [null, Validators.required],
    professorId: [null, Validators.required],
    disciplinaId: [null, Validators.required]
  });

  constructor(
    protected horarioService: HorarioService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected professorService: ProfessorService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ horario }) => {
      this.updateForm(horario);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.turmaService
        .query()
        .pipe(
          map((res: HttpResponse<ITurma[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITurma[]) => (this.turmas = resBody));

      this.professorService
        .query()
        .pipe(
          map((res: HttpResponse<IProfessor[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProfessor[]) => (this.professors = resBody));

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

  updateForm(horario: IHorario): void {
    this.editForm.patchValue({
      id: horario.id,
      inicioAula: horario.inicioAula != null ? horario.inicioAula.format(DATE_TIME_FORMAT) : null,
      terminoAlua: horario.terminoAlua != null ? horario.terminoAlua.format(DATE_TIME_FORMAT) : null,
      intervalo: horario.intervalo != null ? horario.intervalo.format(DATE_TIME_FORMAT) : null,
      diaSemana: horario.diaSemana,
      regimeCurricular: horario.regimeCurricular,
      data: horario.data != null ? horario.data.format(DATE_TIME_FORMAT) : null,
      anoLectivo: horario.anoLectivo,
      categoria: horario.categoria,
      utilizadorId: horario.utilizadorId,
      turmaId: horario.turmaId,
      professorId: horario.professorId,
      disciplinaId: horario.disciplinaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const horario = this.createFromForm();
    if (horario.id !== undefined) {
      this.subscribeToSaveResponse(this.horarioService.update(horario));
    } else {
      this.subscribeToSaveResponse(this.horarioService.create(horario));
    }
  }

  private createFromForm(): IHorario {
    return {
      ...new Horario(),
      id: this.editForm.get(['id'])!.value,
      inicioAula:
        this.editForm.get(['inicioAula'])!.value != null ? moment(this.editForm.get(['inicioAula'])!.value, DATE_TIME_FORMAT) : undefined,
      terminoAlua:
        this.editForm.get(['terminoAlua'])!.value != null ? moment(this.editForm.get(['terminoAlua'])!.value, DATE_TIME_FORMAT) : undefined,
      intervalo:
        this.editForm.get(['intervalo'])!.value != null ? moment(this.editForm.get(['intervalo'])!.value, DATE_TIME_FORMAT) : undefined,
      diaSemana: this.editForm.get(['diaSemana'])!.value,
      regimeCurricular: this.editForm.get(['regimeCurricular'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      turmaId: this.editForm.get(['turmaId'])!.value,
      professorId: this.editForm.get(['professorId'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorario>>): void {
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
