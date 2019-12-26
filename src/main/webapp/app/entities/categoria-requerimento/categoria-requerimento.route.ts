import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaRequerimento, CategoriaRequerimento } from 'app/shared/model/categoria-requerimento.model';
import { CategoriaRequerimentoService } from './categoria-requerimento.service';
import { CategoriaRequerimentoComponent } from './categoria-requerimento.component';
import { CategoriaRequerimentoDetailComponent } from './categoria-requerimento-detail.component';
import { CategoriaRequerimentoUpdateComponent } from './categoria-requerimento-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaRequerimentoResolve implements Resolve<ICategoriaRequerimento> {
  constructor(private service: CategoriaRequerimentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaRequerimento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaRequerimento: HttpResponse<CategoriaRequerimento>) => {
          if (categoriaRequerimento.body) {
            return of(categoriaRequerimento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaRequerimento());
  }
}

export const categoriaRequerimentoRoute: Routes = [
  {
    path: '',
    component: CategoriaRequerimentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.categoriaRequerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaRequerimentoDetailComponent,
    resolve: {
      categoriaRequerimento: CategoriaRequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaRequerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaRequerimentoUpdateComponent,
    resolve: {
      categoriaRequerimento: CategoriaRequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaRequerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaRequerimentoUpdateComponent,
    resolve: {
      categoriaRequerimento: CategoriaRequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.categoriaRequerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
