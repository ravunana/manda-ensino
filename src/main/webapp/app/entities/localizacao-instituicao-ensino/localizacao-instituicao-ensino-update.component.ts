import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILocalizacaoInstituicaoEnsino, LocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';
import { LocalizacaoInstituicaoEnsinoService } from './localizacao-instituicao-ensino.service';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

@Component({
  selector: 'rv-localizacao-instituicao-ensino-update',
  templateUrl: './localizacao-instituicao-ensino-update.component.html'
})
export class LocalizacaoInstituicaoEnsinoUpdateComponent implements OnInit {
  isSaving = false;

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    provincia: [null, [Validators.required]],
    municipio: [null, [Validators.required]],
    bairro: [null, [Validators.required]],
    rua: [null, [Validators.required, Validators.maxLength(200)]],
    quarteirao: [null, [Validators.required, Validators.maxLength(10)]],
    numeroPorta: [null, [Validators.maxLength(10)]],
    caixaPostal: [],
    instituicaoEnsinoId: [null, Validators.required]
  });

  constructor(
    protected localizacaoInstituicaoEnsinoService: LocalizacaoInstituicaoEnsinoService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localizacaoInstituicaoEnsino }) => {
      this.updateForm(localizacaoInstituicaoEnsino);

      this.instituicaoEnsinoService
        .query()
        .pipe(
          map((res: HttpResponse<IInstituicaoEnsino[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInstituicaoEnsino[]) => (this.instituicaoensinos = resBody));
    });
  }

  updateForm(localizacaoInstituicaoEnsino: ILocalizacaoInstituicaoEnsino): void {
    this.editForm.patchValue({
      id: localizacaoInstituicaoEnsino.id,
      provincia: localizacaoInstituicaoEnsino.provincia,
      municipio: localizacaoInstituicaoEnsino.municipio,
      bairro: localizacaoInstituicaoEnsino.bairro,
      rua: localizacaoInstituicaoEnsino.rua,
      quarteirao: localizacaoInstituicaoEnsino.quarteirao,
      numeroPorta: localizacaoInstituicaoEnsino.numeroPorta,
      caixaPostal: localizacaoInstituicaoEnsino.caixaPostal,
      instituicaoEnsinoId: localizacaoInstituicaoEnsino.instituicaoEnsinoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localizacaoInstituicaoEnsino = this.createFromForm();
    if (localizacaoInstituicaoEnsino.id !== undefined) {
      this.subscribeToSaveResponse(this.localizacaoInstituicaoEnsinoService.update(localizacaoInstituicaoEnsino));
    } else {
      this.subscribeToSaveResponse(this.localizacaoInstituicaoEnsinoService.create(localizacaoInstituicaoEnsino));
    }
  }

  private createFromForm(): ILocalizacaoInstituicaoEnsino {
    return {
      ...new LocalizacaoInstituicaoEnsino(),
      id: this.editForm.get(['id'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      rua: this.editForm.get(['rua'])!.value,
      quarteirao: this.editForm.get(['quarteirao'])!.value,
      numeroPorta: this.editForm.get(['numeroPorta'])!.value,
      caixaPostal: this.editForm.get(['caixaPostal'])!.value,
      instituicaoEnsinoId: this.editForm.get(['instituicaoEnsinoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalizacaoInstituicaoEnsino>>): void {
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
