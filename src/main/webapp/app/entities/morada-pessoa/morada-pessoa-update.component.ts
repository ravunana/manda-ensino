import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMoradaPessoa, MoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { MoradaPessoaService } from './morada-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-morada-pessoa-update',
  templateUrl: './morada-pessoa-update.component.html'
})
export class MoradaPessoaUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    provincia: [null, [Validators.required]],
    municipio: [null, [Validators.required]],
    bairro: [null, [Validators.required]],
    rua: [null, [Validators.maxLength(200)]],
    quarteirao: [null, [Validators.maxLength(10)]],
    numeroPorta: [null, [Validators.maxLength(10)]],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected moradaPessoaService: MoradaPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moradaPessoa }) => {
      this.updateForm(moradaPessoa);

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

  updateForm(moradaPessoa: IMoradaPessoa): void {
    this.editForm.patchValue({
      id: moradaPessoa.id,
      provincia: moradaPessoa.provincia,
      municipio: moradaPessoa.municipio,
      bairro: moradaPessoa.bairro,
      rua: moradaPessoa.rua,
      quarteirao: moradaPessoa.quarteirao,
      numeroPorta: moradaPessoa.numeroPorta,
      pessoaId: moradaPessoa.pessoaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moradaPessoa = this.createFromForm();
    if (moradaPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.moradaPessoaService.update(moradaPessoa));
    } else {
      this.subscribeToSaveResponse(this.moradaPessoaService.create(moradaPessoa));
    }
  }

  private createFromForm(): IMoradaPessoa {
    return {
      ...new MoradaPessoa(),
      id: this.editForm.get(['id'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      rua: this.editForm.get(['rua'])!.value,
      quarteirao: this.editForm.get(['quarteirao'])!.value,
      numeroPorta: this.editForm.get(['numeroPorta'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoradaPessoa>>): void {
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
