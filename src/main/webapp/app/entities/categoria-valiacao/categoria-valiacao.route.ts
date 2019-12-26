import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaValiacao, CategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';
import { CategoriaValiacaoService } from './categoria-valiacao.service';
import { CategoriaValiacaoComponent } from './categoria-valiacao.component';
import { CategoriaValiacaoDetailComponent } from './categoria-valiacao-detail.component';
import { CategoriaValiacaoUpdateComponent } from './categoria-valiacao-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaValiacaoResolve implements Resolve<ICategoriaValiacao> {
  constructor(private service: CategoriaValiacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaValiacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaValiacao: HttpResponse<CategoriaValiacao>) => {
          if (categoriaValiacao.body) {
            return of(categoriaValiacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaValiacao());
  }
}

export const categoriaValiacaoRoute: Routes = [
  {
    path: '',
    component: CategoriaValiacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.categoriaValiacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaValiacaoDetailComponent,
    resolve: {
      categoriaValiacao: CategoriaValiacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaValiacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaValiacaoUpdateComponent,
    resolve: {
      categoriaValiacao: CategoriaValiacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaValiacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaValiacaoUpdateComponent,
    resolve: {
      categoriaValiacao: CategoriaValiacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaValiacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
