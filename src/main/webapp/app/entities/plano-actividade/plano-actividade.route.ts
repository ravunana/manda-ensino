import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoActividade, PlanoActividade } from 'app/shared/model/plano-actividade.model';
import { PlanoActividadeService } from './plano-actividade.service';
import { PlanoActividadeComponent } from './plano-actividade.component';
import { PlanoActividadeDetailComponent } from './plano-actividade-detail.component';
import { PlanoActividadeUpdateComponent } from './plano-actividade-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoActividadeResolve implements Resolve<IPlanoActividade> {
  constructor(private service: PlanoActividadeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoActividade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoActividade: HttpResponse<PlanoActividade>) => {
          if (planoActividade.body) {
            return of(planoActividade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoActividade());
  }
}

export const planoActividadeRoute: Routes = [
  {
    path: '',
    component: PlanoActividadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.planoActividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanoActividadeDetailComponent,
    resolve: {
      planoActividade: PlanoActividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoActividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanoActividadeUpdateComponent,
    resolve: {
      planoActividade: PlanoActividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoActividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanoActividadeUpdateComponent,
    resolve: {
      planoActividade: PlanoActividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoActividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
