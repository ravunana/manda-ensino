import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICoordenadaBancaria, CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

@Component({
  selector: 'rv-coordenada-bancaria-update',
  templateUrl: './coordenada-bancaria-update.component.html'
})
export class CoordenadaBancariaUpdateComponent implements OnInit {
  isSaving = false;

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required]],
    proprietario: [null, [Validators.required]],
    numeroConta: [null, [Validators.required]],
    iban: [null, []],
    ativo: [null, [Validators.required]],
    mostrarDocumento: [],
    mostrarPontoVenda: [],
    padraoRecebimento: [],
    padraoPagamento: [],
    instituicaoEnsinos: [null, Validators.required]
  });

  constructor(
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coordenadaBancaria }) => {
      this.updateForm(coordenadaBancaria);

      this.instituicaoEnsinoService
        .query()
        .pipe(
          map((res: HttpResponse<IInstituicaoEnsino[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInstituicaoEnsino[]) => (this.instituicaoensinos = resBody));
    });
  }

  updateForm(coordenadaBancaria: ICoordenadaBancaria): void {
    this.editForm.patchValue({
      id: coordenadaBancaria.id,
      descricao: coordenadaBancaria.descricao,
      proprietario: coordenadaBancaria.proprietario,
      numeroConta: coordenadaBancaria.numeroConta,
      iban: coordenadaBancaria.iban,
      ativo: coordenadaBancaria.ativo,
      mostrarDocumento: coordenadaBancaria.mostrarDocumento,
      mostrarPontoVenda: coordenadaBancaria.mostrarPontoVenda,
      padraoRecebimento: coordenadaBancaria.padraoRecebimento,
      padraoPagamento: coordenadaBancaria.padraoPagamento,
      instituicaoEnsinos: coordenadaBancaria.instituicaoEnsinos
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const coordenadaBancaria = this.createFromForm();
    if (coordenadaBancaria.id !== undefined) {
      this.subscribeToSaveResponse(this.coordenadaBancariaService.update(coordenadaBancaria));
    } else {
      this.subscribeToSaveResponse(this.coordenadaBancariaService.create(coordenadaBancaria));
    }
  }

  private createFromForm(): ICoordenadaBancaria {
    return {
      ...new CoordenadaBancaria(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      proprietario: this.editForm.get(['proprietario'])!.value,
      numeroConta: this.editForm.get(['numeroConta'])!.value,
      iban: this.editForm.get(['iban'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      mostrarDocumento: this.editForm.get(['mostrarDocumento'])!.value,
      mostrarPontoVenda: this.editForm.get(['mostrarPontoVenda'])!.value,
      padraoRecebimento: this.editForm.get(['padraoRecebimento'])!.value,
      padraoPagamento: this.editForm.get(['padraoPagamento'])!.value,
      instituicaoEnsinos: this.editForm.get(['instituicaoEnsinos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoordenadaBancaria>>): void {
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

  trackById(index: number, item: IInstituicaoEnsino): any {
    return item.id;
  }

  getSelected(selectedVals: IInstituicaoEnsino[], option: IInstituicaoEnsino): IInstituicaoEnsino {
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
