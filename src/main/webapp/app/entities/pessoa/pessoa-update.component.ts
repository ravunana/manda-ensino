import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPessoa, Pessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-pessoa-update',
  templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];
  nascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    tipoPessoa: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    imagem: [],
    imagemContentType: [],
    pai: [],
    mae: [],
    nascimento: [],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected pessoaService: PessoaService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.updateForm(pessoa);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(pessoa: IPessoa): void {
    this.editForm.patchValue({
      id: pessoa.id,
      tipoPessoa: pessoa.tipoPessoa,
      nome: pessoa.nome,
      imagem: pessoa.imagem,
      imagemContentType: pessoa.imagemContentType,
      pai: pessoa.pai,
      mae: pessoa.mae,
      nascimento: pessoa.nascimento,
      utilizadorId: pessoa.utilizadorId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ensinoApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoa = this.createFromForm();
    if (pessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  private createFromForm(): IPessoa {
    return {
      ...new Pessoa(),
      id: this.editForm.get(['id'])!.value,
      tipoPessoa: this.editForm.get(['tipoPessoa'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      imagemContentType: this.editForm.get(['imagemContentType'])!.value,
      imagem: this.editForm.get(['imagem'])!.value,
      pai: this.editForm.get(['pai'])!.value,
      mae: this.editForm.get(['mae'])!.value,
      nascimento: this.editForm.get(['nascimento'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
