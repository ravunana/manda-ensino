import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeposito, Deposito } from 'app/shared/model/deposito.model';
import { DepositoService } from './deposito.service';
import { DepositoComponent } from './deposito.component';
import { DepositoDetailComponent } from './deposito-detail.component';
import { DepositoUpdateComponent } from './deposito-update.component';

@Injectable({ providedIn: 'root' })
export class DepositoResolve implements Resolve<IDeposito> {
  constructor(private service: DepositoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeposito> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deposito: HttpResponse<Deposito>) => {
          if (deposito.body) {
            return of(deposito.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Deposito());
  }
}

export const depositoRoute: Routes = [
  {
    path: '',
    component: DepositoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.deposito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepositoDetailComponent,
    resolve: {
      deposito: DepositoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.deposito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepositoUpdateComponent,
    resolve: {
      deposito: DepositoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.deposito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepositoUpdateComponent,
    resolve: {
      deposito: DepositoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.deposito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
