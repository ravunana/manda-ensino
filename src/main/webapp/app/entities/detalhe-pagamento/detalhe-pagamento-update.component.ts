import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDetalhePagamento, DetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';
import { DetalhePagamentoService } from './detalhe-pagamento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEmolumento } from 'app/shared/model/emolumento.model';
import { EmolumentoService } from 'app/entities/emolumento/emolumento.service';
import { IDeposito } from 'app/shared/model/deposito.model';
import { DepositoService } from 'app/entities/deposito/deposito.service';

type SelectableEntity = IUser | IEmolumento | IDeposito;

@Component({
  selector: 'rv-detalhe-pagamento-update',
  templateUrl: './detalhe-pagamento-update.component.html'
})
export class DetalhePagamentoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  emolumentos: IEmolumento[] = [];

  depositos: IDeposito[] = [];
  vencimentoDp: any;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    mensalidade: [null, [Validators.required]],
    quantidade: [null, [Validators.required, Validators.min(1)]],
    valor: [null, [Validators.required, Validators.min(0)]],
    desconto: [null, [Validators.min(0)]],
    multa: [null, [Validators.min(0)]],
    juro: [null, [Validators.min(0)]],
    data: [],
    vencimento: [],
    quitado: [null, [Validators.required]],
    utilizadorId: [null, Validators.required],
    emolumentoId: [null, Validators.required],
    depositoId: [null, Validators.required]
  });

  constructor(
    protected detalhePagamentoService: DetalhePagamentoService,
    protected userService: UserService,
    protected emolumentoService: EmolumentoService,
    protected depositoService: DepositoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalhePagamento }) => {
      this.updateForm(detalhePagamento);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.emolumentoService
        .query()
        .pipe(
          map((res: HttpResponse<IEmolumento[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEmolumento[]) => (this.emolumentos = resBody));

      this.depositoService
        .query()
        .pipe(
          map((res: HttpResponse<IDeposito[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDeposito[]) => (this.depositos = resBody));
    });
  }

  updateForm(detalhePagamento: IDetalhePagamento): void {
    this.editForm.patchValue({
      id: detalhePagamento.id,
      descricao: detalhePagamento.descricao,
      mensalidade: detalhePagamento.mensalidade,
      quantidade: detalhePagamento.quantidade,
      valor: detalhePagamento.valor,
      desconto: detalhePagamento.desconto,
      multa: detalhePagamento.multa,
      juro: detalhePagamento.juro,
      data: detalhePagamento.data != null ? detalhePagamento.data.format(DATE_TIME_FORMAT) : null,
      vencimento: detalhePagamento.vencimento,
      quitado: detalhePagamento.quitado,
      utilizadorId: detalhePagamento.utilizadorId,
      emolumentoId: detalhePagamento.emolumentoId,
      depositoId: detalhePagamento.depositoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalhePagamento = this.createFromForm();
    if (detalhePagamento.id !== undefined) {
      this.subscribeToSaveResponse(this.detalhePagamentoService.update(detalhePagamento));
    } else {
      this.subscribeToSaveResponse(this.detalhePagamentoService.create(detalhePagamento));
    }
  }

  private createFromForm(): IDetalhePagamento {
    return {
      ...new DetalhePagamento(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      mensalidade: this.editForm.get(['mensalidade'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      desconto: this.editForm.get(['desconto'])!.value,
      multa: this.editForm.get(['multa'])!.value,
      juro: this.editForm.get(['juro'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      vencimento: this.editForm.get(['vencimento'])!.value,
      quitado: this.editForm.get(['quitado'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      emolumentoId: this.editForm.get(['emolumentoId'])!.value,
      depositoId: this.editForm.get(['depositoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalhePagamento>>): void {
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
