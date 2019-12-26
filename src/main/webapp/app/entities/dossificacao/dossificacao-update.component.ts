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

import { IDossificacao, Dossificacao } from 'app/shared/model/dossificacao.model';
import { DossificacaoService } from './dossificacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from 'app/entities/curso/curso.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';

type SelectableEntity = ICurso | IClasse | IDisciplina;

type SelectableManyToManyEntity = ICurso | IClasse;

@Component({
  selector: 'rv-dossificacao-update',
  templateUrl: './dossificacao-update.component.html'
})
export class DossificacaoUpdateComponent implements OnInit {
  isSaving = false;

  cursos: ICurso[] = [];

  classes: IClasse[] = [];

  disciplinas: IDisciplina[] = [];
  anoLectivoDp: any;
  deDp: any;
  ateDp: any;

  editForm = this.fb.group({
    id: [],
    periodoLectivo: [null, [Validators.required]],
    anoLectivo: [null, [Validators.required]],
    objectivoGeral: [null, [Validators.required]],
    objectivoEspecifico: [null, [Validators.required]],
    semanaLectiva: [null, [Validators.required]],
    de: [null, [Validators.required]],
    ate: [null, [Validators.required]],
    conteudo: [null, [Validators.required]],
    procedimentoEnsino: [null, [Validators.required]],
    recursosDidatico: [null, [Validators.required]],
    tempoAula: [null, [Validators.required]],
    formaAvaliacao: [null, [Validators.required]],
    cursos: [null, Validators.required],
    classes: [null, Validators.required],
    disciplinaId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected dossificacaoService: DossificacaoService,
    protected cursoService: CursoService,
    protected classeService: ClasseService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossificacao }) => {
      this.updateForm(dossificacao);

      this.cursoService
        .query()
        .pipe(
          map((res: HttpResponse<ICurso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICurso[]) => (this.cursos = resBody));

      this.classeService
        .query()
        .pipe(
          map((res: HttpResponse<IClasse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IClasse[]) => (this.classes = resBody));

      this.disciplinaService
        .query()
        .pipe(
          map((res: HttpResponse<IDisciplina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDisciplina[]) => (this.disciplinas = resBody));
    });
  }

  updateForm(dossificacao: IDossificacao): void {
    this.editForm.patchValue({
      id: dossificacao.id,
      periodoLectivo: dossificacao.periodoLectivo,
      anoLectivo: dossificacao.anoLectivo,
      objectivoGeral: dossificacao.objectivoGeral,
      objectivoEspecifico: dossificacao.objectivoEspecifico,
      semanaLectiva: dossificacao.semanaLectiva,
      de: dossificacao.de,
      ate: dossificacao.ate,
      conteudo: dossificacao.conteudo,
      procedimentoEnsino: dossificacao.procedimentoEnsino,
      recursosDidatico: dossificacao.recursosDidatico,
      tempoAula: dossificacao.tempoAula != null ? dossificacao.tempoAula.format(DATE_TIME_FORMAT) : null,
      formaAvaliacao: dossificacao.formaAvaliacao,
      cursos: dossificacao.cursos,
      classes: dossificacao.classes,
      disciplinaId: dossificacao.disciplinaId
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
    const dossificacao = this.createFromForm();
    if (dossificacao.id !== undefined) {
      this.subscribeToSaveResponse(this.dossificacaoService.update(dossificacao));
    } else {
      this.subscribeToSaveResponse(this.dossificacaoService.create(dossificacao));
    }
  }

  private createFromForm(): IDossificacao {
    return {
      ...new Dossificacao(),
      id: this.editForm.get(['id'])!.value,
      periodoLectivo: this.editForm.get(['periodoLectivo'])!.value,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      objectivoGeral: this.editForm.get(['objectivoGeral'])!.value,
      objectivoEspecifico: this.editForm.get(['objectivoEspecifico'])!.value,
      semanaLectiva: this.editForm.get(['semanaLectiva'])!.value,
      de: this.editForm.get(['de'])!.value,
      ate: this.editForm.get(['ate'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      procedimentoEnsino: this.editForm.get(['procedimentoEnsino'])!.value,
      recursosDidatico: this.editForm.get(['recursosDidatico'])!.value,
      tempoAula:
        this.editForm.get(['tempoAula'])!.value != null ? moment(this.editForm.get(['tempoAula'])!.value, DATE_TIME_FORMAT) : undefined,
      formaAvaliacao: this.editForm.get(['formaAvaliacao'])!.value,
      cursos: this.editForm.get(['cursos'])!.value,
      classes: this.editForm.get(['classes'])!.value,
      disciplinaId: this.editForm.get(['disciplinaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossificacao>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
