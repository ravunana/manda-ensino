import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IContactoPessoa, ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { ContactoPessoaService } from './contacto-pessoa.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';

@Component({
  selector: 'rv-contacto-pessoa-update',
  templateUrl: './contacto-pessoa-update.component.html'
})
export class ContactoPessoaUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  editForm = this.fb.group({
    id: [],
    tipoContacto: [null, [Validators.required]],
    descricao: [null, [Validators.required]],
    contacto: [null, [Validators.required]],
    pessoaId: [null, Validators.required]
  });

  constructor(
    protected contactoPessoaService: ContactoPessoaService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactoPessoa }) => {
      this.updateForm(contactoPessoa);

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

  updateForm(contactoPessoa: IContactoPessoa): void {
    this.editForm.patchValue({
      id: contactoPessoa.id,
      tipoContacto: contactoPessoa.tipoContacto,
      descricao: contactoPessoa.descricao,
      contacto: contactoPessoa.contacto,
      pessoaId: contactoPessoa.pessoaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactoPessoa = this.createFromForm();
    if (contactoPessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoPessoaService.update(contactoPessoa));
    } else {
      this.subscribeToSaveResponse(this.contactoPessoaService.create(contactoPessoa));
    }
  }

  private createFromForm(): IContactoPessoa {
    return {
      ...new ContactoPessoa(),
      id: this.editForm.get(['id'])!.value,
      tipoContacto: this.editForm.get(['tipoContacto'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      contacto: this.editForm.get(['contacto'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactoPessoa>>): void {
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
