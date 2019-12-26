import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITurma, Turma } from 'app/shared/model/turma.model';
import { TurmaService } from './turma.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISala } from 'app/shared/model/sala.model';
import { SalaService } from 'app/entities/sala/sala.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';
import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from 'app/entities/curso/curso.service';

type SelectableEntity = IUser | ISala | IClasse | ICurso;

@Component({
  selector: 'rv-turma-update',
  templateUrl: './turma-update.component.html'
})
export class TurmaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  salas: ISala[] = [];

  classes: IClasse[] = [];

  cursos: ICurso[] = [];
  anoLectivoDp: any;

  editForm = this.fb.group({
    id: [],
    descricao: [null],
    anoLectivo: [],
    regime: [null, [Validators.required]],
    turno: [null, [Validators.required]],
    data: [],
    ativo: [],
    utilizadorId: [],
    salaId: [null, Validators.required],
    classeId: [null, Validators.required],
    cursoId: [null, Validators.required]
  });

  constructor(
    protected turmaService: TurmaService,
    protected userService: UserService,
    protected salaService: SalaService,
    protected classeService: ClasseService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turma }) => {
      this.updateForm(turma);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.salaService
        .query()
        .pipe(
          map((res: HttpResponse<ISala[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISala[]) => (this.salas = resBody));

      this.classeService
        .query()
        .pipe(
          map((res: HttpResponse<IClasse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IClasse[]) => (this.classes = resBody));

      this.cursoService
        .query()
        .pipe(
          map((res: HttpResponse<ICurso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICurso[]) => (this.cursos = resBody));
    });
  }

  updateForm(turma: ITurma): void {
    this.editForm.patchValue({
      id: turma.id,
      descricao: turma.descricao,
      anoLectivo: turma.anoLectivo,
      regime: turma.regime,
      turno: turma.turno,
      data: turma.data != null ? turma.data.format(DATE_TIME_FORMAT) : null,
      ativo: turma.ativo,
      utilizadorId: turma.utilizadorId,
      salaId: turma.salaId,
      classeId: turma.classeId,
      cursoId: turma.cursoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const turma = this.createFromForm();
    if (turma.id !== undefined) {
      this.subscribeToSaveResponse(this.turmaService.update(turma));
    } else {
      this.subscribeToSaveResponse(this.turmaService.create(turma));
    }
  }

  private createFromForm(): ITurma {
    return {
      ...new Turma(),
      id: this.editForm.get(['id'])!.value,
      descricao: '',
      anoLectivo: moment(),
      // descricao: this.editForm.get(['descricao'])!.value,
      // anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      regime: this.editForm.get(['regime'])!.value,
      turno: this.editForm.get(['turno'])!.value.toUpperCase(),
      data: moment(),
      // data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      ativo: this.editForm.get(['ativo'])!.value,
      utilizadorId: 1,
      // utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      salaId: this.editForm.get(['salaId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value,
      cursoId: this.editForm.get(['cursoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurma>>): void {
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
