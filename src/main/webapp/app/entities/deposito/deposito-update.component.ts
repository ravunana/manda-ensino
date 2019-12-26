import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IDeposito, Deposito } from 'app/shared/model/deposito.model';
import { DepositoService } from './deposito.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from 'app/entities/meio-liquidacao/meio-liquidacao.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';

type SelectableEntity = IUser | IMeioLiquidacao | IAluno | ICoordenadaBancaria;

@Component({
  selector: 'rv-deposito-update',
  templateUrl: './deposito-update.component.html'
})
export class DepositoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  meioliquidacaos: IMeioLiquidacao[] = [];

  alunos: IAluno[] = [];

  coordenadabancarias: ICoordenadaBancaria[] = [];
  dataDepositoDp: any;

  editForm = this.fb.group({
    id: [],
    numeroTalao: [null, [Validators.required]],
    dataDeposito: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    saldo: [null, [Validators.required, Validators.min(0)]],
    utilizadorId: [null, Validators.required],
    meioLiquidacaoId: [null, Validators.required],
    alunoId: [null, Validators.required],
    contaId: [null, Validators.required]
  });

  constructor(
    protected depositoService: DepositoService,
    protected userService: UserService,
    protected meioLiquidacaoService: MeioLiquidacaoService,
    protected alunoService: AlunoService,
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deposito }) => {
      this.updateForm(deposito);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.meioLiquidacaoService
        .query()
        .pipe(
          map((res: HttpResponse<IMeioLiquidacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMeioLiquidacao[]) => (this.meioliquidacaos = resBody));

      this.alunoService
        .query()
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => (this.alunos = resBody));

      this.coordenadaBancariaService
        .query()
        .pipe(
          map((res: HttpResponse<ICoordenadaBancaria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICoordenadaBancaria[]) => (this.coordenadabancarias = resBody));
    });
  }

  updateForm(deposito: IDeposito): void {
    this.editForm.patchValue({
      id: deposito.id,
      numeroTalao: deposito.numeroTalao,
      dataDeposito: deposito.dataDeposito,
      valor: deposito.valor,
      saldo: deposito.saldo,
      utilizadorId: deposito.utilizadorId,
      meioLiquidacaoId: deposito.meioLiquidacaoId,
      alunoId: deposito.alunoId,
      contaId: deposito.contaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deposito = this.createFromForm();
    if (deposito.id !== undefined) {
      this.subscribeToSaveResponse(this.depositoService.update(deposito));
    } else {
      this.subscribeToSaveResponse(this.depositoService.create(deposito));
    }
  }

  private createFromForm(): IDeposito {
    return {
      ...new Deposito(),
      id: this.editForm.get(['id'])!.value,
      numeroTalao: this.editForm.get(['numeroTalao'])!.value,
      dataDeposito: this.editForm.get(['dataDeposito'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      saldo: this.editForm.get(['saldo'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      meioLiquidacaoId: this.editForm.get(['meioLiquidacaoId'])!.value,
      alunoId: this.editForm.get(['alunoId'])!.value,
      contaId: this.editForm.get(['contaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeposito>>): void {
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
