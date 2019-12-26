import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICriterioAvaliacao, CriterioAvaliacao } from 'app/shared/model/criterio-avaliacao.model';
import { CriterioAvaliacaoService } from './criterio-avaliacao.service';
import { CriterioAvaliacaoComponent } from './criterio-avaliacao.component';
import { CriterioAvaliacaoDetailComponent } from './criterio-avaliacao-detail.component';
import { CriterioAvaliacaoUpdateComponent } from './criterio-avaliacao-update.component';

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoResolve implements Resolve<ICriterioAvaliacao> {
  constructor(private service: CriterioAvaliacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICriterioAvaliacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((criterioAvaliacao: HttpResponse<CriterioAvaliacao>) => {
          if (criterioAvaliacao.body) {
            return of(criterioAvaliacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CriterioAvaliacao());
  }
}

export const criterioAvaliacaoRoute: Routes = [
  {
    path: '',
    component: CriterioAvaliacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.criterioAvaliacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CriterioAvaliacaoDetailComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.criterioAvaliacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CriterioAvaliacaoUpdateComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.criterioAvaliacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CriterioAvaliacaoUpdateComponent,
    resolve: {
      criterioAvaliacao: CriterioAvaliacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.criterioAvaliacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
