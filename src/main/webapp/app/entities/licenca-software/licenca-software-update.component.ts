import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILicencaSoftware, LicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';
import { ISoftware } from 'app/shared/model/software.model';
import { SoftwareService } from 'app/entities/software/software.service';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/instituicao-ensino.service';

type SelectableEntity = ISoftware | IInstituicaoEnsino;

@Component({
  selector: 'rv-licenca-software-update',
  templateUrl: './licenca-software-update.component.html'
})
export class LicencaSoftwareUpdateComponent implements OnInit {
  isSaving = false;

  softwares: ISoftware[] = [];

  instituicaoensinos: IInstituicaoEnsino[] = [];

  editForm = this.fb.group({
    id: [],
    tipoSubscricao: [null, [Validators.required]],
    inicio: [null, [Validators.required]],
    fim: [null, [Validators.required]],
    data: [],
    valor: [null, [Validators.required, Validators.min(0)]],
    codigo: [null, [Validators.required]],
    numeroUsuario: [null, [Validators.min(1)]],
    numeroInstituicaoEnsino: [null, [Validators.min(1)]],
    softwareId: [],
    instituicaoEnsinoId: [null, Validators.required]
  });

  constructor(
    protected licencaSoftwareService: LicencaSoftwareService,
    protected softwareService: SoftwareService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licencaSoftware }) => {
      this.updateForm(licencaSoftware);

      this.softwareService
        .query()
        .pipe(
          map((res: HttpResponse<ISoftware[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISoftware[]) => (this.softwares = resBody));

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

  updateForm(licencaSoftware: ILicencaSoftware): void {
    this.editForm.patchValue({
      id: licencaSoftware.id,
      tipoSubscricao: licencaSoftware.tipoSubscricao,
      inicio: licencaSoftware.inicio != null ? licencaSoftware.inicio.format(DATE_TIME_FORMAT) : null,
      fim: licencaSoftware.fim != null ? licencaSoftware.fim.format(DATE_TIME_FORMAT) : null,
      data: licencaSoftware.data != null ? licencaSoftware.data.format(DATE_TIME_FORMAT) : null,
      valor: licencaSoftware.valor,
      codigo: licencaSoftware.codigo,
      numeroUsuario: licencaSoftware.numeroUsuario,
      numeroInstituicaoEnsino: licencaSoftware.numeroInstituicaoEnsino,
      softwareId: licencaSoftware.softwareId,
      instituicaoEnsinoId: licencaSoftware.instituicaoEnsinoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const licencaSoftware = this.createFromForm();
    if (licencaSoftware.id !== undefined) {
      this.subscribeToSaveResponse(this.licencaSoftwareService.update(licencaSoftware));
    } else {
      this.subscribeToSaveResponse(this.licencaSoftwareService.create(licencaSoftware));
    }
  }

  private createFromForm(): ILicencaSoftware {
    return {
      ...new LicencaSoftware(),
      id: this.editForm.get(['id'])!.value,
      tipoSubscricao: this.editForm.get(['tipoSubscricao'])!.value,
      inicio: this.editForm.get(['inicio'])!.value != null ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      fim: this.editForm.get(['fim'])!.value != null ? moment(this.editForm.get(['fim'])!.value, DATE_TIME_FORMAT) : undefined,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      valor: this.editForm.get(['valor'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      numeroUsuario: this.editForm.get(['numeroUsuario'])!.value,
      numeroInstituicaoEnsino: this.editForm.get(['numeroInstituicaoEnsino'])!.value,
      softwareId: this.editForm.get(['softwareId'])!.value,
      instituicaoEnsinoId: this.editForm.get(['instituicaoEnsinoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicencaSoftware>>): void {
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
