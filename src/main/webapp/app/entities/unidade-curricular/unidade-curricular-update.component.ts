import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUnidadeCurricular, UnidadeCurricular } from 'app/shared/model/unidade-curricular.model';
import { UnidadeCurricularService } from './unidade-curricular.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';

type SelectableEntity = IDisciplina | IClasse | IUnidadeCurricular;

@Component({
  selector: 'rv-unidade-curricular-update',
  templateUrl: './unidade-curricular-update.component.html'
})
export class UnidadeCurricularUpdateComponent implements OnInit {
  isSaving = false;

  disciplinas: IDisciplina[] = [];

  classes: IClasse[] = [];

  unidadecurriculars: IUnidadeCurricular[] = [];

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    unidade: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    disciplinaId: [null, Validators.required],
    classeId: [null, Validators.required],
    herarquiaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected unidadeCurricularService: UnidadeCurricularService,
    protected disciplinaService: DisciplinaService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unidadeCurricular }) => {
      this.updateForm(unidadeCurricular);

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

      this.unidadeCurricularService
        .query()
        .pipe(
          map((res: HttpResponse<IUnidadeCurricular[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUnidadeCurricular[]) => (this.unidadecurriculars = resBody));
    });
  }

  updateForm(unidadeCurricular: IUnidadeCurricular): void {
    this.editForm.patchValue({
      id: unidadeCurricular.id,
      descricao: unidadeCurricular.descricao,
      unidade: unidadeCurricular.unidade,
      numero: unidadeCurricular.numero,
      disciplinaId: unidadeCurricular.disciplinaId,
      classeId: unidadeCurricular.classeId,
      herarquiaId: unidadeCurricular.herarquiaId
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
    const unidadeCurricular = this.createFromForm();
    if (unidadeCurricular.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadeCurricularService.update(unidadeCurricular));
    } else {
      this.subscribeToSaveResponse(this.unidadeCurricularService.create(unidadeCurricular));
    }
  }

  private createFromForm(): IUnidadeCurricular {
    return {
      ...new UnidadeCurricular(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      unidade: this.editForm.get(['unidade'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value,
      herarquiaId: this.editForm.get(['herarquiaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadeCurricular>>): void {
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
