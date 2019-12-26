import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDocumentoMatricula, DocumentoMatricula } from 'app/shared/model/documento-matricula.model';
import { DocumentoMatriculaService } from './documento-matricula.service';
import { IMatricula } from 'app/shared/model/matricula.model';
import { MatriculaService } from 'app/entities/matricula/matricula.service';

@Component({
  selector: 'rv-documento-matricula-update',
  templateUrl: './documento-matricula-update.component.html'
})
export class DocumentoMatriculaUpdateComponent implements OnInit {
  isSaving = false;

  matriculas: IMatricula[] = [];

  editForm = this.fb.group({
    id: [],
    fotografia: [null, [Validators.required]],
    certificado: [null, [Validators.required]],
    bilhete: [null, [Validators.required]],
    resenciamentoMilitar: [],
    cartaoVacina: [],
    atestadoMedico: [],
    fichaTrnasferencia: [],
    historicoEscolar: [],
    cedula: [],
    descricao: [],
    anoLectivo: [],
    data: [],
    matriculaId: []
  });

  constructor(
    protected documentoMatriculaService: DocumentoMatriculaService,
    protected matriculaService: MatriculaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentoMatricula }) => {
      this.updateForm(documentoMatricula);

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

  updateForm(documentoMatricula: IDocumentoMatricula): void {
    this.editForm.patchValue({
      id: documentoMatricula.id,
      fotografia: documentoMatricula.fotografia,
      certificado: documentoMatricula.certificado,
      bilhete: documentoMatricula.bilhete,
      resenciamentoMilitar: documentoMatricula.resenciamentoMilitar,
      cartaoVacina: documentoMatricula.cartaoVacina,
      atestadoMedico: documentoMatricula.atestadoMedico,
      fichaTrnasferencia: documentoMatricula.fichaTrnasferencia,
      historicoEscolar: documentoMatricula.historicoEscolar,
      cedula: documentoMatricula.cedula,
      descricao: documentoMatricula.descricao,
      anoLectivo: documentoMatricula.anoLectivo,
      data: documentoMatricula.data != null ? documentoMatricula.data.format(DATE_TIME_FORMAT) : null,
      matriculaId: documentoMatricula.matriculaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentoMatricula = this.createFromForm();
    if (documentoMatricula.id !== undefined) {
      this.subscribeToSaveResponse(this.documentoMatriculaService.update(documentoMatricula));
    } else {
      this.subscribeToSaveResponse(this.documentoMatriculaService.create(documentoMatricula));
    }
  }

  private createFromForm(): IDocumentoMatricula {
    return {
      ...new DocumentoMatricula(),
      id: this.editForm.get(['id'])!.value,
      fotografia: this.editForm.get(['fotografia'])!.value,
      certificado: this.editForm.get(['certificado'])!.value,
      bilhete: this.editForm.get(['bilhete'])!.value,
      resenciamentoMilitar: this.editForm.get(['resenciamentoMilitar'])!.value,
      cartaoVacina: this.editForm.get(['cartaoVacina'])!.value,
      atestadoMedico: this.editForm.get(['atestadoMedico'])!.value,
      fichaTrnasferencia: this.editForm.get(['fichaTrnasferencia'])!.value,
      historicoEscolar: this.editForm.get(['historicoEscolar'])!.value,
      cedula: this.editForm.get(['cedula'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      anoLectivo: this.editForm.get(['anoLectivo'])!.value,
      data: this.editForm.get(['data'])!.value != null ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      matriculaId: this.editForm.get(['matriculaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentoMatricula>>): void {
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

  trackById(index: number, item: IMatricula): any {
    return item.id;
  }
}
