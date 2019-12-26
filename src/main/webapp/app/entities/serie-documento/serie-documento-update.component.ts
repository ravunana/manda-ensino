import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISerieDocumento, SerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

@Component({
  selector: 'rv-serie-documento-update',
  templateUrl: './serie-documento-update.component.html'
})
export class SerieDocumentoUpdateComponent implements OnInit {
  isSaving = false;

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    serie: [],
    sequencia: [],
    entidade: [],
    instituicaoEnsinoId: []
  });

  constructor(
    protected serieDocumentoService: SerieDocumentoService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serieDocumento }) => {
      this.updateForm(serieDocumento);

      this.instituicaoEnsinoService
        .query({ filter: 'seriedocumento-is-null' })
        .pipe(
          map((res: HttpResponse<IInstituicaoEnsino[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInstituicaoEnsino[]) => {
          if (!serieDocumento.instituicaoEnsinoId) {
            this.instituicaoensinos = resBody;
          } else {
            this.instituicaoEnsinoService
              .find(serieDocumento.instituicaoEnsinoId)
              .pipe(
                map((subRes: HttpResponse<IInstituicaoEnsino>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInstituicaoEnsino[]) => {
                this.instituicaoensinos = concatRes;
              });
          }
        });
    });
  }

  updateForm(serieDocumento: ISerieDocumento): void {
    this.editForm.patchValue({
      id: serieDocumento.id,
      serie: serieDocumento.serie,
      sequencia: serieDocumento.sequencia,
      entidade: serieDocumento.entidade,
      instituicaoEnsinoId: serieDocumento.instituicaoEnsinoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serieDocumento = this.createFromForm();
    if (serieDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.serieDocumentoService.update(serieDocumento));
    } else {
      this.subscribeToSaveResponse(this.serieDocumentoService.create(serieDocumento));
    }
  }

  private createFromForm(): ISerieDocumento {
    return {
      ...new SerieDocumento(),
      id: this.editForm.get(['id'])!.value,
      serie: this.editForm.get(['serie'])!.value,
      sequencia: this.editForm.get(['sequencia'])!.value,
      entidade: this.editForm.get(['entidade'])!.value,
      instituicaoEnsinoId: this.editForm.get(['instituicaoEnsinoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISerieDocumento>>): void {
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

  trackById(index: number, item: IInstituicaoEnsino): any {
    return item.id;
  }
}
