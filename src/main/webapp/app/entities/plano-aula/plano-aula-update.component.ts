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

import { IPlanoAula, PlanoAula } from 'app/shared/model/plano-aula.model';
import { PlanoAulaService } from './plano-aula.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITurma } from 'app/shared/model/turma.model';
import { TurmaService } from 'app/entities/turma/turma.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';
import { IDossificacao } from 'app/shared/model/dossificacao.model';
import { DossificacaoService } from 'app/entities/dossificacao/dossificacao.service';

type SelectableEntity = IUser | ITurma | IDisciplina | IDossificacao;

@Component({
  selector: 'rv-plano-aula-update',
  templateUrl: './plano-aula-update.component.html'
})
export class PlanoAulaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  turmas: ITurma[] = [];

  disciplinas: IDisciplina[] = [];

  dossificacaos: IDossificacao[] = [];

  editForm = this.fb.group({
    id: [],
    objectivoGeral: [null, [Validators.required]],
    objectivoEspecifico: [null, [Validators.required]],
    conteudo: [null, [Validators.required]],
    estrategia: [null, [Validators.required]],
    actividades: [null, [Validators.required]],
    tempo: [null, [Validators.required]],
    recursosEnsino: [null, [Validators.required]],
    avaliacao: [null, [Validators.required]],
    observacao: [null, [Validators.required]],
    bibliografia: [null, [Validators.required]],
    perfilEntrada: [null, [Validators.required]],
    perfilSaida: [null, [Validators.required]],
    anexo1: [],
    anexo1ContentType: [],
    anexo2: [],
    anexo2ContentType: [],
    anexo3: [],
    anexo3ContentType: [],
    utilizadorId: [],
    turmas: [null, Validators.required],
    disciplinaId: [null, Validators.required],
    dossificacaoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected planoAulaService: PlanoAulaService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected disciplinaService: DisciplinaService,
    protected dossificacaoService: DossificacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAula }) => {
      this.updateForm(planoAula);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.turmaService
        .query()
        .pipe(
          map((res: HttpResponse<ITurma[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITurma[]) => (this.turmas = resBody));

      this.disciplinaService
        .query()
        .pipe(
          map((res: HttpResponse<IDisciplina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDisciplina[]) => (this.disciplinas = resBody));

      this.dossificacaoService
        .query()
        .pipe(
          map((res: HttpResponse<IDossificacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDossificacao[]) => (this.dossificacaos = resBody));
    });
  }

  updateForm(planoAula: IPlanoAula): void {
    this.editForm.patchValue({
      id: planoAula.id,
      objectivoGeral: planoAula.objectivoGeral,
      objectivoEspecifico: planoAula.objectivoEspecifico,
      conteudo: planoAula.conteudo,
      estrategia: planoAula.estrategia,
      actividades: planoAula.actividades,
      tempo: planoAula.tempo != null ? planoAula.tempo.format(DATE_TIME_FORMAT) : null,
      recursosEnsino: planoAula.recursosEnsino,
      avaliacao: planoAula.avaliacao,
      observacao: planoAula.observacao,
      bibliografia: planoAula.bibliografia,
      perfilEntrada: planoAula.perfilEntrada,
      perfilSaida: planoAula.perfilSaida,
      anexo1: planoAula.anexo1,
      anexo1ContentType: planoAula.anexo1ContentType,
      anexo2: planoAula.anexo2,
      anexo2ContentType: planoAula.anexo2ContentType,
      anexo3: planoAula.anexo3,
      anexo3ContentType: planoAula.anexo3ContentType,
      utilizadorId: planoAula.utilizadorId,
      turmas: planoAula.turmas,
      disciplinaId: planoAula.disciplinaId,
      dossificacaoId: planoAula.dossificacaoId
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
    const planoAula = this.createFromForm();
    if (planoAula.id !== undefined) {
      this.subscribeToSaveResponse(this.planoAulaService.update(planoAula));
    } else {
      this.subscribeToSaveResponse(this.planoAulaService.create(planoAula));
    }
  }

  private createFromForm(): IPlanoAula {
    return {
      ...new PlanoAula(),
      id: this.editForm.get(['id'])!.value,
      objectivoGeral: this.editForm.get(['objectivoGeral'])!.value,
      objectivoEspecifico: this.editForm.get(['objectivoEspecifico'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      estrategia: this.editForm.get(['estrategia'])!.value,
      actividades: this.editForm.get(['actividades'])!.value,
      tempo: this.editForm.get(['tempo'])!.value != null ? moment(this.editForm.get(['tempo'])!.value, DATE_TIME_FORMAT) : undefined,
      recursosEnsino: this.editForm.get(['recursosEnsino'])!.value,
      avaliacao: this.editForm.get(['avaliacao'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      bibliografia: this.editForm.get(['bibliografia'])!.value,
      perfilEntrada: this.editForm.get(['perfilEntrada'])!.value,
      perfilSaida: this.editForm.get(['perfilSaida'])!.value,
      anexo1ContentType: this.editForm.get(['anexo1ContentType'])!.value,
      anexo1: this.editForm.get(['anexo1'])!.value,
      anexo2ContentType: this.editForm.get(['anexo2ContentType'])!.value,
      anexo2: this.editForm.get(['anexo2'])!.value,
      anexo3ContentType: this.editForm.get(['anexo3ContentType'])!.value,
      anexo3: this.editForm.get(['anexo3'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      turmas: this.editForm.get(['turmas'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value,
      dossificacaoId: this.editForm.get(['dossificacaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoAula>>): void {
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

  getSelected(selectedVals: ITurma[], option: ITurma): ITurma {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
