import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFichaMedica, FichaMedica } from 'app/shared/model/ficha-medica.model';
import { FichaMedicaService } from './ficha-medica.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IAluno | IUser;

@Component({
  selector: 'rv-ficha-medica-update',
  templateUrl: './ficha-medica-update.component.html'
})
export class FichaMedicaUpdateComponent implements OnInit {
  isSaving = false;

  alunos: IAluno[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fazEducacaoFisica: [null, [Validators.required]],
    grupoSanguinio: [],
    altura: [],
    peso: [],
    autorizaMedicamento: [null, [Validators.required]],
    observacao: [],
    nomeMedico: [],
    contactoMedico: [],
    desmaioConstante: [],
    complicacoesSaude: [],
    alunoId: [null, Validators.required],
    utilizadorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fichaMedicaService: FichaMedicaService,
    protected alunoService: AlunoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fichaMedica }) => {
      this.updateForm(fichaMedica);

      this.alunoService
        .query({ filter: 'fichamedica-is-null' })
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => {
          if (!fichaMedica.alunoId) {
            this.alunos = resBody;
          } else {
            this.alunoService
              .find(fichaMedica.alunoId)
              .pipe(
                map((subRes: HttpResponse<IAluno>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAluno[]) => {
                this.alunos = concatRes;
              });
          }
        });

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

  updateForm(fichaMedica: IFichaMedica): void {
    this.editForm.patchValue({
      id: fichaMedica.id,
      fazEducacaoFisica: fichaMedica.fazEducacaoFisica,
      grupoSanguinio: fichaMedica.grupoSanguinio,
      altura: fichaMedica.altura,
      peso: fichaMedica.peso,
      autorizaMedicamento: fichaMedica.autorizaMedicamento,
      observacao: fichaMedica.observacao,
      nomeMedico: fichaMedica.nomeMedico,
      contactoMedico: fichaMedica.contactoMedico,
      desmaioConstante: fichaMedica.desmaioConstante,
      complicacoesSaude: fichaMedica.complicacoesSaude,
      alunoId: fichaMedica.alunoId,
      utilizadorId: fichaMedica.utilizadorId
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
    const fichaMedica = this.createFromForm();
    if (fichaMedica.id !== undefined) {
      this.subscribeToSaveResponse(this.fichaMedicaService.update(fichaMedica));
    } else {
      this.subscribeToSaveResponse(this.fichaMedicaService.create(fichaMedica));
    }
  }

  private createFromForm(): IFichaMedica {
    return {
      ...new FichaMedica(),
      id: this.editForm.get(['id'])!.value,
      fazEducacaoFisica: this.editForm.get(['fazEducacaoFisica'])!.value,
      grupoSanguinio: this.editForm.get(['grupoSanguinio'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      autorizaMedicamento: this.editForm.get(['autorizaMedicamento'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      nomeMedico: this.editForm.get(['nomeMedico'])!.value,
      contactoMedico: this.editForm.get(['contactoMedico'])!.value,
      desmaioConstante: this.editForm.get(['desmaioConstante'])!.value,
      complicacoesSaude: this.editForm.get(['complicacoesSaude'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichaMedica>>): void {
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
