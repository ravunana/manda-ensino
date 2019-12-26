import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMeioLiquidacao, MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from './meio-liquidacao.service';

@Component({
  selector: 'rv-meio-liquidacao-update',
  templateUrl: './meio-liquidacao-update.component.html'
})
export class MeioLiquidacaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    sigla: [null, []],
    icon: [],
    mostrarPontoVenda: [null, [Validators.required]]
  });

  constructor(protected meioLiquidacaoService: MeioLiquidacaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meioLiquidacao }) => {
      this.updateForm(meioLiquidacao);
    });
  }

  updateForm(meioLiquidacao: IMeioLiquidacao): void {
    this.editForm.patchValue({
      id: meioLiquidacao.id,
      nome: meioLiquidacao.nome,
      sigla: meioLiquidacao.sigla,
      icon: meioLiquidacao.icon,
      mostrarPontoVenda: meioLiquidacao.mostrarPontoVenda
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meioLiquidacao = this.createFromForm();
    if (meioLiquidacao.id !== undefined) {
      this.subscribeToSaveResponse(this.meioLiquidacaoService.update(meioLiquidacao));
    } else {
      this.subscribeToSaveResponse(this.meioLiquidacaoService.create(meioLiquidacao));
    }
  }

  private createFromForm(): IMeioLiquidacao {
    return {
      ...new MeioLiquidacao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sigla: this.editForm.get(['sigla'])!.value,
      icon: this.editForm.get(['icon'])!.value,
      mostrarPontoVenda: this.editForm.get(['mostrarPontoVenda'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeioLiquidacao>>): void {
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
}
