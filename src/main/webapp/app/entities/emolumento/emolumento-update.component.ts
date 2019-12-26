import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEmolumento, Emolumento } from 'app/shared/model/emolumento.model';
import { EmolumentoService } from './emolumento.service';
import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from 'app/entities/curso/curso.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';

type SelectableEntity = ICurso | IClasse;

@Component({
  selector: 'rv-emolumento-update',
  templateUrl: './emolumento-update.component.html'
})
export class EmolumentoUpdateComponent implements OnInit {
  isSaving = false;

  cursos: ICurso[] = [];

  classes: IClasse[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    valor: [null, [Validators.required, Validators.min(0)]],
    valorMulta: [null, [Validators.required, Validators.min(0)]],
    tempoMulta: [null, [Validators.required, Validators.min(1)]],
    quantidade: [null, [Validators.min(0)]],
    cursoId: [],
    classeId: []
  });

  constructor(
    protected emolumentoService: EmolumentoService,
    protected cursoService: CursoService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emolumento }) => {
      this.updateForm(emolumento);

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
    });
  }

  updateForm(emolumento: IEmolumento): void {
    this.editForm.patchValue({
      id: emolumento.id,
      nome: emolumento.nome,
      valor: emolumento.valor,
      valorMulta: emolumento.valorMulta,
      tempoMulta: emolumento.tempoMulta,
      quantidade: emolumento.quantidade,
      cursoId: emolumento.cursoId,
      classeId: emolumento.classeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emolumento = this.createFromForm();
    if (emolumento.id !== undefined) {
      this.subscribeToSaveResponse(this.emolumentoService.update(emolumento));
    } else {
      this.subscribeToSaveResponse(this.emolumentoService.create(emolumento));
    }
  }

  private createFromForm(): IEmolumento {
    return {
      ...new Emolumento(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      valorMulta: this.editForm.get(['valorMulta'])!.value,
      tempoMulta: this.editForm.get(['tempoMulta'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      cursoId: this.editForm.get(['cursoId'])!.value,
      classeId: this.editForm.get(['classeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmolumento>>): void {
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
