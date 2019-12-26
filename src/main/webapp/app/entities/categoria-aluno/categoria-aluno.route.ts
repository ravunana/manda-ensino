import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaAluno, CategoriaAluno } from 'app/shared/model/categoria-aluno.model';
import { CategoriaAlunoService } from './categoria-aluno.service';
import { CategoriaAlunoComponent } from './categoria-aluno.component';
import { CategoriaAlunoDetailComponent } from './categoria-aluno-detail.component';
import { CategoriaAlunoUpdateComponent } from './categoria-aluno-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaAlunoResolve implements Resolve<ICategoriaAluno> {
  constructor(private service: CategoriaAlunoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaAluno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaAluno: HttpResponse<CategoriaAluno>) => {
          if (categoriaAluno.body) {
            return of(categoriaAluno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaAluno());
  }
}

export const categoriaAlunoRoute: Routes = [
  {
    path: '',
    component: CategoriaAlunoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.categoriaAluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaAlunoDetailComponent,
    resolve: {
      categoriaAluno: CategoriaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaAluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaAlunoUpdateComponent,
    resolve: {
      categoriaAluno: CategoriaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaAluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaAlunoUpdateComponent,
    resolve: {
      categoriaAluno: CategoriaAlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaAluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
