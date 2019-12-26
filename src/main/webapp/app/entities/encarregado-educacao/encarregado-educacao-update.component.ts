import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEncarregadoEducacao, EncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';
import { EncarregadoEducacaoService } from './encarregado-educacao.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-encarregado-educacao-update',
  templateUrl: './encarregado-educacao-update.component.html'
})
export class EncarregadoEducacaoUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    profissao: [],
    cargo: [],
    faixaSalarial: [],
    nomeEmpresa: [],
    contactoEmpresa: [],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encarregadoEducacao }) => {
      this.updateForm(encarregadoEducacao);

      this.pessoaService
        .query({ filter: 'encarregadoeducacao-is-null' })
        .pipe(
          map((res: HttpResponse<IPessoa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPessoa[]) => {
          if (!encarregadoEducacao.pessoaId) {
            this.pessoas = resBody;
          } else {
            this.pessoaService
              .find(encarregadoEducacao.pessoaId)
              .pipe(
                map((subRes: HttpResponse<IPessoa>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPessoa[]) => {
                this.pessoas = concatRes;
              });
          }
        });
    });
  }

  updateForm(encarregadoEducacao: IEncarregadoEducacao): void {
    this.editForm.patchValue({
      id: encarregadoEducacao.id,
      profissao: encarregadoEducacao.profissao,
      cargo: encarregadoEducacao.cargo,
      faixaSalarial: encarregadoEducacao.faixaSalarial,
      nomeEmpresa: encarregadoEducacao.nomeEmpresa,
      contactoEmpresa: encarregadoEducacao.contactoEmpresa,
      pessoaId: encarregadoEducacao.pessoaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encarregadoEducacao = this.createFromForm();
    if (encarregadoEducacao.id !== undefined) {
      this.subscribeToSaveResponse(this.encarregadoEducacaoService.update(encarregadoEducacao));
    } else {
      this.subscribeToSaveResponse(this.encarregadoEducacaoService.create(encarregadoEducacao));
    }
  }

  private createFromForm(): IEncarregadoEducacao {
    return {
      ...new EncarregadoEducacao(),
      id: this.editForm.get(['id'])!.value,
      profissao: this.editForm.get(['profissao'])!.value,
      cargo: this.editForm.get(['cargo'])!.value,
      faixaSalarial: this.editForm.get(['faixaSalarial'])!.value,
      nomeEmpresa: this.editForm.get(['nomeEmpresa'])!.value,
      contactoEmpresa: this.editForm.get(['contactoEmpresa'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncarregadoEducacao>>): void {
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

  trackById(index: number, item: IPessoa): any {
    return item.id;
  }
}
