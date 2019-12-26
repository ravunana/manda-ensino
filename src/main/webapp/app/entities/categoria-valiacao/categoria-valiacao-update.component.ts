import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICategoriaValiacao, CategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';
import { CategoriaValiacaoService } from './categoria-valiacao.service';
import { IAreaFormacao } from 'app/shared/model/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/area-formacao.service';

@Component({
  selector: 'rv-categoria-valiacao-update',
  templateUrl: './categoria-valiacao-update.component.html'
})
export class CategoriaValiacaoUpdateComponent implements OnInit {
  isSaving = false;

  areaformacaos: IAreaFormacao[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    siglaInterna: [null, []],
    siglaPauta: [null, [Validators.required]],
    areaFormacaoId: [null, Validators.required]
  });

  constructor(
    protected categoriaValiacaoService: CategoriaValiacaoService,
    protected areaFormacaoService: AreaFormacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaValiacao }) => {
      this.updateForm(categoriaValiacao);

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

  updateForm(categoriaValiacao: ICategoriaValiacao): void {
    this.editForm.patchValue({
      id: categoriaValiacao.id,
      nome: categoriaValiacao.nome,
      siglaInterna: categoriaValiacao.siglaInterna,
      siglaPauta: categoriaValiacao.siglaPauta,
      areaFormacaoId: categoriaValiacao.areaFormacaoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaValiacao = this.createFromForm();
    if (categoriaValiacao.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaValiacaoService.update(categoriaValiacao));
    } else {
      this.subscribeToSaveResponse(this.categoriaValiacaoService.create(categoriaValiacao));
    }
  }

  private createFromForm(): ICategoriaValiacao {
    return {
      ...new CategoriaValiacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      siglaInterna: this.editForm.get(['siglaInterna'])!.value,
      siglaPauta: this.editForm.get(['siglaPauta'])!.value,
      areaFormacaoId: this.editForm.get(['areaFormacaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaValiacao>>): void {
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
