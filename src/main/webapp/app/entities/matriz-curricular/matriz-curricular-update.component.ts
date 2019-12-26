import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMatrizCurricular, MatrizCurricular } from 'app/shared/model/matriz-curricular.model';
import { MatrizCurricularService } from './matriz-curricular.service';
import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from 'app/entities/curso/curso.service';

@Component({
  selector: 'rv-matriz-curricular-update',
  templateUrl: './matriz-curricular-update.component.html'
})
export class MatrizCurricularUpdateComponent implements OnInit {
  isSaving = false;

  cursos: ICurso[] = [];

  editForm = this.fb.group({
    id: [],
    cursoId: [null, Validators.required]
  });

  constructor(
    protected matrizCurricularService: MatrizCurricularService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matrizCurricular }) => {
      this.updateForm(matrizCurricular);

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

  updateForm(matrizCurricular: IMatrizCurricular): void {
    this.editForm.patchValue({
      id: matrizCurricular.id,
      cursoId: matrizCurricular.cursoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matrizCurricular = this.createFromForm();
    if (matrizCurricular.id !== undefined) {
      this.subscribeToSaveResponse(this.matrizCurricularService.update(matrizCurricular));
    } else {
      this.subscribeToSaveResponse(this.matrizCurricularService.create(matrizCurricular));
    }
  }

  private createFromForm(): IMatrizCurricular {
    return {
      ...new MatrizCurricular(),
      id: this.editForm.get(['id'])!.value,
      cursoId: this.editForm.get(['cursoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatrizCurricular>>): void {
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

  trackById(index: number, item: ICurso): any {
    return item.id;
  }
}
