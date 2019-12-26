import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPagamento, Pagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from './pagamento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IFormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from 'app/entities/forma-liquidacao/forma-liquidacao.service';

type SelectableEntity = IUser | IAluno | IFormaLiquidacao;

@Component({
  selector: 'rv-pagamento-update',
  templateUrl: './pagamento-update.component.html'
})
export class PagamentoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  alunos: IAluno[] = [];

  formaliquidacaos: IFormaLiquidacao[] = [];

  editForm = this.fb.group({
    id: [],
    data: [],
    numero: [null, [Validators.required]],
    utilizadorId: [null, Validators.required],
    alunoId: [null, Validators.required],
    formaLiquidacaoId: [null, Validators.required]
  });

  constructor(
    protected pagamentoService: PagamentoService,
    protected userService: UserService,
    protected alunoService: AlunoService,
    protected formaLiquidacaoService: FormaLiquidacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagamento }) => {
      this.updateForm(pagamento);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));

      this.formaLiquidacaoService
        .query()
        .pipe(
          map((res: HttpResponse<IFormaLiquidacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IFormaLiquidacao[]) => (this.formaliquidacaos = resBody));
    });
  }

  updateForm(pagamento: IPagamento): void {
    this.editForm.patchValue({
      id: pagamento.id,
      data: pagamento.data != null ? pagamento.data.format(DATE_TIME_FORMAT) : null,
      numero: pagamento.numero,
      utilizadorId: pagamento.utilizadorId,
      alunoId: pagamento.alunoId,
      formaLiquidacaoId: pagamento.formaLiquidacaoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pagamento = this.createFromForm();
    if (pagamento.id !== undefined) {
      this.subscribeToSaveResponse(this.pagamentoService.update(pagamento));
    } else {
      this.subscribeToSaveResponse(this.pagamentoService.create(pagamento));
    }
  }

  private createFromForm(): IPagamento {
    return {
      ...new Pagamento(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      numero: this.editForm.get(['numero'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value,
      formaLiquidacaoId: this.editForm.get(['formaLiquidacaoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagamento>>): void {
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
