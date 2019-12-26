import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICriterioAvaliacao, CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';
import { CriterioAvaliacaoService } from './criterio-avaliacao.service';
import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { PlanoCurricularService } from 'app/entities/plano-curricular/plano-curricular.service';

@Component({
  selector: 'rv-criterio-avaliacao-update',
  templateUrl: './criterio-avaliacao-update.component.html'
})
export class CriterioAvaliacaoUpdateComponent implements OnInit {
  isSaving = false;

  planocurriculars: IPlanoCurricular[] = [];

  editForm = this.fb.group({
    id: [],
    aprovaCom: [null, [Validators.required, Validators.min(0)]],
    reporvaCom: [null, [Validators.required, Validators.min(0)]],
    recursoCom: [null, [Validators.required, Validators.min(0)]],
    fazExame: [null, [Validators.required]],
    fazRecurso: [null, [Validators.required]],
    fazExameEspecial: [null, [Validators.required]],
    numeroFaltaReprova: [null, [Validators.required, Validators.min(0)]],
    menorNota: [null, [Validators.required, Validators.min(0)]],
    maiorNota: [null, [Validators.required, Validators.min(0)]],
    notaMinimaAprovacao: [null, [Validators.required, Validators.min(0)]],
    planoCurricularId: []
  });

  constructor(
    protected criterioAvaliacaoService: CriterioAvaliacaoService,
    protected planoCurricularService: PlanoCurricularService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criterioAvaliacao }) => {
      this.updateForm(criterioAvaliacao);

      this.planoCurricularService
        .query({ filter: 'criterioavaliacao-is-null' })
        .pipe(
          map((res: HttpResponse<IPlanoCurricular[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlanoCurricular[]) => {
          if (!criterioAvaliacao.planoCurricularId) {
            this.planocurriculars = resBody;
          } else {
            this.planoCurricularService
              .find(criterioAvaliacao.planoCurricularId)
              .pipe(
                map((subRes: HttpResponse<IPlanoCurricular>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPlanoCurricular[]) => {
                this.planocurriculars = concatRes;
              });
          }
        });
    });
  }

  updateForm(criterioAvaliacao: ICriterioAvaliacao): void {
    this.editForm.patchValue({
      id: criterioAvaliacao.id,
      aprovaCom: criterioAvaliacao.aprovaCom,
      reporvaCom: criterioAvaliacao.reporvaCom,
      recursoCom: criterioAvaliacao.recursoCom,
      fazExame: criterioAvaliacao.fazExame,
      fazRecurso: criterioAvaliacao.fazRecurso,
      fazExameEspecial: criterioAvaliacao.fazExameEspecial,
      numeroFaltaReprova: criterioAvaliacao.numeroFaltaReprova,
      menorNota: criterioAvaliacao.menorNota,
      maiorNota: criterioAvaliacao.maiorNota,
      notaMinimaAprovacao: criterioAvaliacao.notaMinimaAprovacao,
      planoCurricularId: criterioAvaliacao.planoCurricularId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criterioAvaliacao = this.createFromForm();
    if (criterioAvaliacao.id !== undefined) {
      this.subscribeToSaveResponse(this.criterioAvaliacaoService.update(criterioAvaliacao));
    } else {
      this.subscribeToSaveResponse(this.criterioAvaliacaoService.create(criterioAvaliacao));
    }
  }

  private createFromForm(): ICriterioAvaliacao {
    return {
      ...new CriterioAvaliacao(),
      id: this.editForm.get(['id'])!.value,
      aprovaCom: this.editForm.get(['aprovaCom'])!.value,
      reporvaCom: this.editForm.get(['reporvaCom'])!.value,
      recursoCom: this.editForm.get(['recursoCom'])!.value,
      fazExame: this.editForm.get(['fazExame'])!.value,
      fazRecurso: this.editForm.get(['fazRecurso'])!.value,
      fazExameEspecial: this.editForm.get(['fazExameEspecial'])!.value,
      numeroFaltaReprova: this.editForm.get(['numeroFaltaReprova'])!.value,
      menorNota: this.editForm.get(['menorNota'])!.value,
      maiorNota: this.editForm.get(['maiorNota'])!.value,
      notaMinimaAprovacao: this.editForm.get(['notaMinimaAprovacao'])!.value,
      planoCurricularId: this.editForm.get(['planoCurricularId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriterioAvaliacao>>): void {
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

  trackById(index: number, item: IPlanoCurricular): any {
    return item.id;
  }
}
