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

import { IRequerimento, Requerimento } from 'app/shared/model/requerimento.model';
import { RequerimentoService } from './requerimento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';
import { CategoriaRequerimentoService } from 'app/entities/categoria-requerimento/categoria-requerimento.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';

type SelectableEntity = IUser | ICategoriaRequerimento | IAluno;

@Component({
  selector: 'rv-requerimento-update',
  templateUrl: './requerimento-update.component.html'
})
export class RequerimentoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  categoriarequerimentos: ICategoriaRequerimento[] = [];

  alunos: IAluno[] = [];

  editForm = this.fb.group({
    id: [],
    requerimento: [],
    requerimentoContentType: [],
    data: [],
    statusRequerimento: [],
    utilizadorId: [],
    categoriaId: [],
    alunoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected requerimentoService: RequerimentoService,
    protected userService: UserService,
    protected categoriaRequerimentoService: CategoriaRequerimentoService,
    protected alunoService: AlunoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requerimento }) => {
      this.updateForm(requerimento);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.categoriaRequerimentoService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoriaRequerimento[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoriaRequerimento[]) => (this.categoriarequerimentos = resBody));

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));
    });
  }

  updateForm(requerimento: IRequerimento): void {
    this.editForm.patchValue({
      id: requerimento.id,
      requerimento: requerimento.requerimento,
      requerimentoContentType: requerimento.requerimentoContentType,
      data: requerimento.data != null ? requerimento.data.format(DATE_TIME_FORMAT) : null,
      statusRequerimento: requerimento.statusRequerimento,
      utilizadorId: requerimento.utilizadorId,
      categoriaId: requerimento.categoriaId,
      alunoId: requerimento.alunoId
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
    const requerimento = this.createFromForm();
    if (requerimento.id !== undefined) {
      this.subscribeToSaveResponse(this.requerimentoService.update(requerimento));
    } else {
      this.subscribeToSaveResponse(this.requerimentoService.create(requerimento));
    }
  }

  private createFromForm(): IRequerimento {
    return {
      ...new Requerimento(),
      id: this.editForm.get(['id'])!.value,
      requerimentoContentType: this.editForm.get(['requerimentoContentType'])!.value,
      requerimento: this.editForm.get(['requerimento'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      statusRequerimento: this.editForm.get(['statusRequerimento'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      categoriaId: this.editForm.get(['categoriaId'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequerimento>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
