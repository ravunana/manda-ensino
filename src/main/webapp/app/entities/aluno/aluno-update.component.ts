import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAluno, Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/encarregado-educacao.service';

type SelectableEntity = IPessoa | IUser | IEncarregadoEducacao;

@Component({
  selector: 'rv-aluno-update',
  templateUrl: './aluno-update.component.html'
})
export class AlunoUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  users: IUser[] = [];

  encarregadoeducacaos: IEncarregadoEducacao[] = [];

  editForm = this.fb.group({
    id: [],
    numeroProcesso: [null, [Validators.required]],
    transferido: [null, [Validators.required]],
    data: [],
    turmaAnterior: [],
    anoConclusao: [],
    cursoFrequentado: [],
    nomeEscolaAnteriror: [],
    enderecoEscolaAnterior: [],
    classeConcluida: [],
    numeroProcessoAnterior: [],
    situacaoAnterior: [],
    pessoaId: [null, Validators.required],
    utilizadorId: [],
    encarregadoEducacaoId: []
  });

  constructor(
    protected alunoService: AlunoService,
    protected pessoaService: PessoaService,
    protected userService: UserService,
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aluno }) => {
      this.updateForm(aluno);

      this.pessoaService
        .query({ filter: 'aluno-is-null' })
        .pipe(
          map((res: HttpResponse<IPessoa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPessoa[]) => {
          if (!aluno.pessoaId) {
            this.pessoas = resBody;
          } else {
            this.pessoaService
              .find(aluno.pessoaId)
              .pipe(
                map((subRes: HttpResponse<IPessoa>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPessoa[]) => {
                this.pessoas = concatRes;
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

      this.encarregadoEducacaoService
        .query()
        .pipe(
          map((res: HttpResponse<IEncarregadoEducacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEncarregadoEducacao[]) => (this.encarregadoeducacaos = resBody));
    });
  }

  updateForm(aluno: IAluno): void {
    this.editForm.patchValue({
      id: aluno.id,
      numeroProcesso: aluno.numeroProcesso,
      transferido: aluno.transferido,
      data: aluno.data != null ? aluno.data.format(DATE_TIME_FORMAT) : null,
      turmaAnterior: aluno.turmaAnterior,
      anoConclusao: aluno.anoConclusao,
      cursoFrequentado: aluno.cursoFrequentado,
      nomeEscolaAnteriror: aluno.nomeEscolaAnteriror,
      enderecoEscolaAnterior: aluno.enderecoEscolaAnterior,
      classeConcluida: aluno.classeConcluida,
      numeroProcessoAnterior: aluno.numeroProcessoAnterior,
      situacaoAnterior: aluno.situacaoAnterior,
      pessoaId: aluno.pessoaId,
      utilizadorId: aluno.utilizadorId,
      encarregadoEducacaoId: aluno.encarregadoEducacaoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aluno = this.createFromForm();
    if (aluno.id !== undefined) {
      this.subscribeToSaveResponse(this.alunoService.update(aluno));
    } else {
      this.subscribeToSaveResponse(this.alunoService.create(aluno));
    }
  }

  private createFromForm(): IAluno {
    return {
      ...new Aluno(),
      id: this.editForm.get(['id'])!.value,
      numeroProcesso: this.editForm.get(['numeroProcesso'])!.value,
      transferido: this.editForm.get(['transferido'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      turmaAnterior: this.editForm.get(['turmaAnterior'])!.value,
      anoConclusao: this.editForm.get(['anoConclusao'])!.value,
      cursoFrequentado: this.editForm.get(['cursoFrequentado'])!.value,
      nomeEscolaAnteriror: this.editForm.get(['nomeEscolaAnteriror'])!.value,
      enderecoEscolaAnterior: this.editForm.get(['enderecoEscolaAnterior'])!.value,
      classeConcluida: this.editForm.get(['classeConcluida'])!.value,
      numeroProcessoAnterior: this.editForm.get(['numeroProcessoAnterior'])!.value,
      situacaoAnterior: this.editForm.get(['situacaoAnterior'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      encarregadoEducacaoId: this.editForm.get(['encarregadoEducacaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAluno>>): void {
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
