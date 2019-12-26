import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICurso, Curso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAreaFormacao } from 'app/shared/model/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/area-formacao.service';

@Component({
  selector: 'rv-curso-update',
  templateUrl: './curso-update.component.html'
})
export class CursoUpdateComponent implements OnInit {
  isSaving = false;

  areaformacaos: IAreaFormacao[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(10)]],
    sigla: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(12)]],
    competencias: [],
    areaFormacaoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected cursoService: CursoService,
    protected areaFormacaoService: AreaFormacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ curso }) => {
      this.updateForm(curso);

      this.areaFormacaoService
        .query()
        .pipe(
          map((res: HttpResponse<IAreaFormacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAreaFormacao[]) => (this.areaformacaos = resBody));
    });
  }

  updateForm(curso: ICurso): void {
    this.editForm.patchValue({
      id: curso.id,
      nome: curso.nome,
      sigla: curso.sigla,
      competencias: curso.competencias,
      areaFormacaoId: curso.areaFormacaoId
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
    const curso = this.createFromForm();
    if (curso.id !== undefined) {
      this.subscribeToSaveResponse(this.cursoService.update(curso));
    } else {
      this.subscribeToSaveResponse(this.cursoService.create(curso));
    }
  }

  private createFromForm(): ICurso {
    return {
      ...new Curso(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sigla: this.editForm.get(['sigla'])!.value.toUpperCase(),
      competencias: this.editForm.get(['competencias'])!.value,
      areaFormacaoId: this.editForm.get(['areaFormacaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurso>>): void {
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

  trackById(index: number, item: IAreaFormacao): any {
    return item.id;
  }
}
