import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRequerimento, Requerimento } from 'app/shared/model/requerimento.model';
import { RequerimentoService } from './requerimento.service';
import { RequerimentoComponent } from './requerimento.component';
import { RequerimentoDetailComponent } from './requerimento-detail.component';
import { RequerimentoUpdateComponent } from './requerimento-update.component';

@Injectable({ providedIn: 'root' })
export class RequerimentoResolve implements Resolve<IRequerimento> {
  constructor(private service: RequerimentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequerimento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((requerimento: HttpResponse<Requerimento>) => {
          if (requerimento.body) {
            return of(requerimento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Requerimento());
  }
}

export const requerimentoRoute: Routes = [
  {
    path: '',
    component: RequerimentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.requerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequerimentoDetailComponent,
    resolve: {
      requerimento: RequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.requerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequerimentoUpdateComponent,
    resolve: {
      requerimento: RequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.requerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequerimentoUpdateComponent,
    resolve: {
      requerimento: RequerimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.requerimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
