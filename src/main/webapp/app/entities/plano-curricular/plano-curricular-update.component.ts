import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPlanoCurricular, PlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { PlanoCurricularService } from './plano-curricular.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';

type SelectableEntity = IDisciplina | IClasse;

@Component({
  selector: 'rv-plano-curricular-update',
  templateUrl: './plano-curricular-update.component.html'
})
export class PlanoCurricularUpdateComponent implements OnInit {
  isSaving = false;

  disciplinas: IDisciplina[] = [];

  classes: IClasse[] = [];

  editForm = this.fb.group({
    id: [],
    cargaHoraria: [null, [Validators.required]],
    descricao: [null, [Validators.required]],
    terminal: [null, [Validators.required]],
    regimeCurricular: [null, [Validators.required]],
    componente: [null, [Validators.required]],
    disciplinaId: [null, Validators.required],
    classeId: [null, Validators.required]
  });

  constructor(
    protected planoCurricularService: PlanoCurricularService,
    protected disciplinaService: DisciplinaService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoCurricular }) => {
      this.updateForm(planoCurricular);

      this.disciplinaService
        .query()
        .pipe(
          map((res: HttpResponse<IDisciplina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDisciplina[]) => (this.disciplinas = resBody));

      this.classeService
        .query()
        .pipe(
          map((res: HttpResponse<IClasse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IClasse[]) => (this.classes = resBody));
    });
  }

  updateForm(planoCurricular: IPlanoCurricular): void {
    this.editForm.patchValue({
      id: planoCurricular.id,
      cargaHoraria: planoCurricular.cargaHoraria,
      descricao: planoCurricular.descricao,
      terminal: planoCurricular.terminal,
      regimeCurricular: planoCurricular.regimeCurricular,
      componente: planoCurricular.componente,
      disciplinaId: planoCurricular.disciplinaId,
      classeId: planoCurricular.classeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoCurricular = this.createFromForm();
    if (planoCurricular.id !== undefined) {
      this.subscribeToSaveResponse(this.planoCurricularService.update(planoCurricular));
    } else {
      this.subscribeToSaveResponse(this.planoCurricularService.create(planoCurricular));
    }
  }

  private createFromForm(): IPlanoCurricular {
    return {
      ...new PlanoCurricular(),
      id: this.editForm.get(['id'])!.value,
      cargaHoraria: this.editForm.get(['cargaHoraria'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      terminal: this.editForm.get(['terminal'])!.value,
      regimeCurricular: this.editForm.get(['regimeCurricular'])!.value,
      componente: this.editForm.get(['componente'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoCurricular>>): void {
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
