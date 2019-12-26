import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILookup, Lookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';

@Component({
  selector: 'rv-lookup-update',
  templateUrl: './lookup-update.component.html'
})
export class LookupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    entidade: [],
    modificavel: []
  });

  constructor(protected lookupService: LookupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookup }) => {
      this.updateForm(lookup);
    });
  }

  updateForm(lookup: ILookup): void {
    this.editForm.patchValue({
      id: lookup.id,
      nome: lookup.nome,
      entidade: lookup.entidade,
      modificavel: lookup.modificavel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lookup = this.createFromForm();
    if (lookup.id !== undefined) {
      this.subscribeToSaveResponse(this.lookupService.update(lookup));
    } else {
      this.subscribeToSaveResponse(this.lookupService.create(lookup));
    }
  }

  private createFromForm(): ILookup {
    return {
      ...new Lookup(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      entidade: this.editForm.get(['entidade'])!.value,
      modificavel: this.editForm.get(['modificavel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookup>>): void {
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
