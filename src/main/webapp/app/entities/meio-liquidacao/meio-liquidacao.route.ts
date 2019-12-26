import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeioLiquidacao, MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from './meio-liquidacao.service';
import { MeioLiquidacaoComponent } from './meio-liquidacao.component';
import { MeioLiquidacaoDetailComponent } from './meio-liquidacao-detail.component';
import { MeioLiquidacaoUpdateComponent } from './meio-liquidacao-update.component';

@Injectable({ providedIn: 'root' })
export class MeioLiquidacaoResolve implements Resolve<IMeioLiquidacao> {
  constructor(private service: MeioLiquidacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeioLiquidacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meioLiquidacao: HttpResponse<MeioLiquidacao>) => {
          if (meioLiquidacao.body) {
            return of(meioLiquidacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MeioLiquidacao());
  }
}

export const meioLiquidacaoRoute: Routes = [
  {
    path: '',
    component: MeioLiquidacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeioLiquidacaoDetailComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeioLiquidacaoUpdateComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeioLiquidacaoUpdateComponent,
    resolve: {
      meioLiquidacao: MeioLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.meioLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
