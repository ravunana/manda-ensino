import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOcorrencia, Ocorrencia } from 'app/shared/model/ocorrencia.model';
import { OcorrenciaService } from './ocorrencia.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMatricula } from 'app/shared/model/matricula.model';
import { MatriculaService } from 'app/entities/matricula/matricula.service';

type SelectableEntity = IUser | IMatricula;

@Component({
  selector: 'rv-ocorrencia-update',
  templateUrl: './ocorrencia-update.component.html'
})
export class OcorrenciaUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  matriculas: IMatricula[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    data: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    reportarEncarregado: [null, [Validators.required]],
    utilizadorId: [],
    matriculaId: [null, Validators.required]
  });

  constructor(
    protected ocorrenciaService: OcorrenciaService,
    protected userService: UserService,
    protected matriculaService: MatriculaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocorrencia }) => {
      this.updateForm(ocorrencia);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.matriculaService
        .query()
        .pipe(
          map((res: HttpResponse<IMatricula[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMatricula[]) => (this.matriculas = resBody));
    });
  }

  updateForm(ocorrencia: IOcorrencia): void {
    this.editForm.patchValue({
      id: ocorrencia.id,
      tipo: ocorrencia.tipo,
      data: ocorrencia.data != null ? ocorrencia.data.format(DATE_TIME_FORMAT) : null,
      numero: ocorrencia.numero,
      reportarEncarregado: ocorrencia.reportarEncarregado,
      utilizadorId: ocorrencia.utilizadorId,
      matriculaId: ocorrencia.matriculaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ocorrencia = this.createFromForm();
    if (ocorrencia.id !== undefined) {
      this.subscribeToSaveResponse(this.ocorrenciaService.update(ocorrencia));
    } else {
      this.subscribeToSaveResponse(this.ocorrenciaService.create(ocorrencia));
    }
  }

  private createFromForm(): IOcorrencia {
    return {
      ...new Ocorrencia(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      numero: this.editForm.get(['numero'])!.value,
      reportarEncarregado: this.editForm.get(['reportarEncarregado'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value,
      matriculaId: this.editForm.get(['matriculaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOcorrencia>>): void {
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
