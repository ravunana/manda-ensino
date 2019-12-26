import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArquivo, Arquivo } from 'app/shared/model/arquivo.model';
import { ArquivoService } from './arquivo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'rv-arquivo-update',
  templateUrl: './arquivo-update.component.html'
})
export class ArquivoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    entidade: [],
    anexo: [],
    anexoContentType: [],
    codigoEntidade: [],
    data: [],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected arquivoService: ArquivoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arquivo }) => {
      this.updateForm(arquivo);

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

  updateForm(arquivo: IArquivo): void {
    this.editForm.patchValue({
      id: arquivo.id,
      descricao: arquivo.descricao,
      entidade: arquivo.entidade,
      anexo: arquivo.anexo,
      anexoContentType: arquivo.anexoContentType,
      codigoEntidade: arquivo.codigoEntidade,
      data: arquivo.data != null ? arquivo.data.format(DATE_TIME_FORMAT) : null,
      utilizadorId: arquivo.utilizadorId
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arquivo = this.createFromForm();
    if (arquivo.id !== undefined) {
      this.subscribeToSaveResponse(this.arquivoService.update(arquivo));
    } else {
      this.subscribeToSaveResponse(this.arquivoService.create(arquivo));
    }
  }

  private createFromForm(): IArquivo {
    return {
      ...new Arquivo(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      entidade: this.editForm.get(['entidade'])!.value,
      anexoContentType: this.editForm.get(['anexoContentType'])!.value,
      anexo: this.editForm.get(['anexo'])!.value,
      codigoEntidade: this.editForm.get(['codigoEntidade'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArquivo>>): void {
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
