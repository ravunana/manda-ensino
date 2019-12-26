import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IDocumentacaoPessoa, DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';
import { DocumentacaoPessoaService } from './documentacao-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-documentacao-pessoa-update',
  templateUrl: './documentacao-pessoa-update.component.html'
})
export class DocumentacaoPessoaUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];
  emissaoDp: any;
  validadeDp: any;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    emissao: [],
    validade: [],
    naturalidade: [],
    nacionalidade: [],
    localEmissao: [],
    nif: [null, []],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected documentacaoPessoaService: DocumentacaoPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentacaoPessoa }) => {
      this.updateForm(documentacaoPessoa);

      this.pessoaService
        .query()
        .pipe(
          map((res: HttpResponse<IPessoa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPessoa[]) => (this.pessoas = resBody));
    });
  }

  updateForm(documentacaoPessoa: IDocumentacaoPessoa): void {
    this.editForm.patchValue({
      id: documentacaoPessoa.id,
      tipo: documentacaoPessoa.tipo,
      numero: documentacaoPessoa.numero,
      emissao: documentacaoPessoa.emissao,
      validade: documentacaoPessoa.validade,
      naturalidade: documentacaoPessoa.naturalidade,
      nacionalidade: documentacaoPessoa.nacionalidade,
      localEmissao: documentacaoPessoa.localEmissao,
      nif: documentacaoPessoa.nif,
      pessoaId: documentacaoPessoa.pessoaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentacaoPessoa = this.createFromForm();
    if (documentacaoPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.documentacaoPessoaService.update(documentacaoPessoa));
    } else {
      this.subscribeToSaveResponse(this.documentacaoPessoaService.create(documentacaoPessoa));
    }
  }

  private createFromForm(): IDocumentacaoPessoa {
    return {
      ...new DocumentacaoPessoa(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      emissao: this.editForm.get(['emissao'])!.value,
      validade: this.editForm.get(['validade'])!.value,
      naturalidade: this.editForm.get(['naturalidade'])!.value,
      nacionalidade: this.editForm.get(['nacionalidade'])!.value,
      localEmissao: this.editForm.get(['localEmissao'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentacaoPessoa>>): void {
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
