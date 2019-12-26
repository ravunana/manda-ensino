import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProfessor, Professor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IPessoa | IUser;

@Component({
  selector: 'rv-professor-update',
  templateUrl: './professor-update.component.html'
})
export class ProfessorUpdateComponent implements OnInit {
  isSaving = false;

  pessoas: IPessoa[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    numeroAgente: [null, [Validators.required]],
    ativo: [],
    pessoaId: [null, Validators.required],
    utilizadorId: []
  });

  constructor(
    protected professorService: ProfessorService,
    protected pessoaService: PessoaService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professor }) => {
      this.updateForm(professor);

      this.pessoaService
        .query({ filter: 'professor-is-null' })
        .pipe(
          map((res: HttpResponse<IPessoa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPessoa[]) => {
          if (!professor.pessoaId) {
            this.pessoas = resBody;
          } else {
            this.pessoaService
              .find(professor.pessoaId)
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
    });
  }

  updateForm(professor: IProfessor): void {
    this.editForm.patchValue({
      id: professor.id,
      numeroAgente: professor.numeroAgente,
      ativo: professor.ativo,
      pessoaId: professor.pessoaId,
      utilizadorId: professor.utilizadorId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const professor = this.createFromForm();
    if (professor.id !== undefined) {
      this.subscribeToSaveResponse(this.professorService.update(professor));
    } else {
      this.subscribeToSaveResponse(this.professorService.create(professor));
    }
  }

  private createFromForm(): IProfessor {
    return {
      ...new Professor(),
      id: this.editForm.get(['id'])!.value,
      numeroAgente: this.editForm.get(['numeroAgente'])!.value,
      ativo: this.editForm.get(['ativo'])!.value,
      pessoaId: this.editForm.get(['pessoaId'])!.value,
      utilizadorId: this.editForm.get(['utilizadorId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessor>>): void {
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
