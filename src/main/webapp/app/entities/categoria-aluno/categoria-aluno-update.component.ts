import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategoriaAluno, CategoriaAluno } from 'app/shared/model/categoria-aluno.model';
import { CategoriaAlunoService } from './categoria-aluno.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'rv-categoria-aluno-update',
  templateUrl: './categoria-aluno-update.component.html'
})
export class CategoriaAlunoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    desconto: [null, [Validators.required]],
    pagaPropina: [],
    pagaMulta: [],
    descricao: [],
    diaPagamento: [null, [Validators.min(1)]],
    mesAtual: [],
    ativo: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categoriaAlunoService: CategoriaAlunoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaAluno }) => {
      this.updateForm(categoriaAluno);
    });
  }

  updateForm(categoriaAluno: ICategoriaAluno): void {
    this.editForm.patchValue({
      id: categoriaAluno.id,
      nome: categoriaAluno.nome,
      desconto: categoriaAluno.desconto,
      pagaPropina: categoriaAluno.pagaPropina,
      pagaMulta: categoriaAluno.pagaMulta,
      descricao: categoriaAluno.descricao,
      diaPagamento: categoriaAluno.diaPagamento,
      mesAtual: categoriaAluno.mesAtual,
      ativo: categoriaAluno.ativo
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
    const categoriaAluno = this.createFromForm();
    if (categoriaAluno.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaAlunoService.update(categoriaAluno));
    } else {
      this.subscribeToSaveResponse(this.categoriaAlunoService.create(categoriaAluno));
    }
  }

  private createFromForm(): ICategoriaAluno {
    return {
      ...new CategoriaAluno(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      desconto: this.editForm.get(['desconto'])!.value,
      pagaPropina: this.editForm.get(['pagaPropina'])!.value,
      pagaMulta: this.editForm.get(['pagaMulta'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      diaPagamento: this.editForm.get(['diaPagamento'])!.value,
      mesAtual: this.editForm.get(['mesAtual'])!.value,
      ativo: this.editForm.get(['ativo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaAluno>>): void {
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
