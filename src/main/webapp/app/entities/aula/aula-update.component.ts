import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAula, Aula } from 'app/shared/model/aula.model';
import { AulaService } from './aula.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/plano-aula.service';
import { ITurma } from 'app/shared/model/turma.model';
import { TurmaService } from 'app/entities/turma/turma.service';

type SelectableEntity = IUser | IPlanoAula | ITurma;

@Component({
  selector: 'rv-aula-update',
  templateUrl: './aula-update.component.html'
})
export class AulaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  planoaulas: IPlanoAula[] = [];

  turmas: ITurma[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    sumario: [null, [Validators.required]],
    licao: [null, [Validators.required]],
    dada: [null, [Validators.required]],
    utilizadorId: [],
    planoAulas: [],
    turmaId: [null, Validators.required]
  });

  constructor(
    protected aulaService: AulaService,
    protected userService: UserService,
    protected planoAulaService: PlanoAulaService,
    protected turmaService: TurmaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aula }) => {
      this.updateForm(aula);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.planoAulaService
        .query()
        .pipe(
          map((res: HttpResponse<IPlanoAula[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlanoAula[]) => (this.planoaulas = resBody));

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

  updateForm(aula: IAula): void {
    this.editForm.patchValue({
      id: aula.id,
      data: aula.data != null ? aula.data.format(DATE_TIME_FORMAT) : null,
      sumario: aula.sumario,
      licao: aula.licao,
      dada: aula.dada,
      utilizadorId: aula.utilizadorId,
      planoAulas: aula.planoAulas,
      turmaId: aula.turmaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aula = this.createFromForm();
    if (aula.id !== undefined) {
      this.subscribeToSaveResponse(this.aulaService.update(aula));
    } else {
      this.subscribeToSaveResponse(this.aulaService.create(aula));
    }
  }

  private createFromForm(): IAula {
    return {
      ...new Aula(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      sumario: this.editForm.get(['sumario'])!.value,
      licao: this.editForm.get(['licao'])!.value,
      dada: this.editForm.get(['dada'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      planoAulas: this.editForm.get(['planoAulas'])!.value,
      turmaId: this.editForm.get(['turmaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAula>>): void {
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

  getSelected(selectedVals: IPlanoAula[], option: IPlanoAula): IPlanoAula {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
