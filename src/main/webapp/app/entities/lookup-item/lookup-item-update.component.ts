import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILookupItem, LookupItem } from 'app/shared/model/lookup-item.model';
import { LookupItemService } from './lookup-item.service';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup/lookup.service';

@Component({
  selector: 'rv-lookup-item-update',
  templateUrl: './lookup-item-update.component.html'
})
export class LookupItemUpdateComponent implements OnInit {
  isSaving = false;

  lookups: ILookup[] = [];

  editForm = this.fb.group({
    id: [],
    valor: [],
    nome: [],
    type: [],
    lookupId: []
  });

  constructor(
    protected lookupItemService: LookupItemService,
    protected lookupService: LookupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookupItem }) => {
      this.updateForm(lookupItem);

      this.lookupService
        .query()
        .pipe(
          map((res: HttpResponse<ILookup[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILookup[]) => (this.lookups = resBody));
    });
  }

  updateForm(lookupItem: ILookupItem): void {
    this.editForm.patchValue({
      id: lookupItem.id,
      valor: lookupItem.valor,
      nome: lookupItem.nome,
      type: lookupItem.type,
      lookupId: lookupItem.lookupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lookupItem = this.createFromForm();
    if (lookupItem.id !== undefined) {
      this.subscribeToSaveResponse(this.lookupItemService.update(lookupItem));
    } else {
      this.subscribeToSaveResponse(this.lookupItemService.create(lookupItem));
    }
  }

  private createFromForm(): ILookupItem {
    return {
      ...new LookupItem(),
      id: this.editForm.get(['id'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      type: this.editForm.get(['type'])!.value,
      lookupId: this.editForm.get(['lookupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookupItem>>): void {
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

  trackById(index: number, item: ILookup): any {
    return item.id;
  }
}
