import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRelacionamentoPessoa, RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';
import { RelacionamentoPessoaService } from './relacionamento-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-relacionamento-pessoa-update',
  templateUrl: './relacionamento-pessoa-update.component.html'
})
export class RelacionamentoPessoaUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    grauParentesco: [null, [Validators.required]],
    deId: [null, Validators.required],
    paraId: [null, Validators.required]
  });

  constructor(
    protected relacionamentoPessoaService: RelacionamentoPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relacionamentoPessoa }) => {
      this.updateForm(relacionamentoPessoa);

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

  updateForm(relacionamentoPessoa: IRelacionamentoPessoa): void {
    this.editForm.patchValue({
      id: relacionamentoPessoa.id,
      grauParentesco: relacionamentoPessoa.grauParentesco,
      deId: relacionamentoPessoa.deId,
      paraId: relacionamentoPessoa.paraId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relacionamentoPessoa = this.createFromForm();
    if (relacionamentoPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.relacionamentoPessoaService.update(relacionamentoPessoa));
    } else {
      this.subscribeToSaveResponse(this.relacionamentoPessoaService.create(relacionamentoPessoa));
    }
  }

  private createFromForm(): IRelacionamentoPessoa {
    return {
      ...new RelacionamentoPessoa(),
      id: this.editForm.get(['id'])!.value,
      grauParentesco: this.editForm.get(['grauParentesco'])!.value,
      deId: this.editForm.get(['deId'])!.value,
      paraId: this.editForm.get(['paraId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelacionamentoPessoa>>): void {
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
