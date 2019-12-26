import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IContactoInstituicaoEnsino, ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';
import { ContactoInstituicaoEnsinoService } from './contacto-instituicao-ensino.service';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

@Component({
  selector: 'rv-contacto-instituicao-ensino-update',
  templateUrl: './contacto-instituicao-ensino-update.component.html'
})
export class ContactoInstituicaoEnsinoUpdateComponent implements OnInit {
  isSaving = false;

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    tipoContacto: [null, [Validators.required]],
    descricao: [null, [Validators.required]],
    contacto: [null, [Validators.required]],
    instituicaoEnsinoId: [null, Validators.required]
  });

  constructor(
    protected contactoInstituicaoEnsinoService: ContactoInstituicaoEnsinoService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactoInstituicaoEnsino }) => {
      this.updateForm(contactoInstituicaoEnsino);

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

  updateForm(contactoInstituicaoEnsino: IContactoInstituicaoEnsino): void {
    this.editForm.patchValue({
      id: contactoInstituicaoEnsino.id,
      tipoContacto: contactoInstituicaoEnsino.tipoContacto,
      descricao: contactoInstituicaoEnsino.descricao,
      contacto: contactoInstituicaoEnsino.contacto,
      instituicaoEnsinoId: contactoInstituicaoEnsino.instituicaoEnsinoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactoInstituicaoEnsino = this.createFromForm();
    if (contactoInstituicaoEnsino.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoInstituicaoEnsinoService.update(contactoInstituicaoEnsino));
    } else {
      this.subscribeToSaveResponse(this.contactoInstituicaoEnsinoService.create(contactoInstituicaoEnsino));
    }
  }

  private createFromForm(): IContactoInstituicaoEnsino {
    return {
      ...new ContactoInstituicaoEnsino(),
      id: this.editForm.get(['id'])!.value,
      tipoContacto: this.editForm.get(['tipoContacto'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      contacto: this.editForm.get(['contacto'])!.value,
      instituicaoEnsinoId: this.editForm.get(['instituicaoEnsinoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactoInstituicaoEnsino>>): void {
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
