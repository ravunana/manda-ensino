import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFormaLiquidacao, FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from './forma-liquidacao.service';
import { FormaLiquidacaoComponent } from './forma-liquidacao.component';
import { FormaLiquidacaoDetailComponent } from './forma-liquidacao-detail.component';
import { FormaLiquidacaoUpdateComponent } from './forma-liquidacao-update.component';

@Injectable({ providedIn: 'root' })
export class FormaLiquidacaoResolve implements Resolve<IFormaLiquidacao> {
  constructor(private service: FormaLiquidacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormaLiquidacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((formaLiquidacao: HttpResponse<FormaLiquidacao>) => {
          if (formaLiquidacao.body) {
            return of(formaLiquidacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormaLiquidacao());
  }
}

export const formaLiquidacaoRoute: Routes = [
  {
    path: '',
    component: FormaLiquidacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormaLiquidacaoDetailComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormaLiquidacaoUpdateComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormaLiquidacaoUpdateComponent,
    resolve: {
      formaLiquidacao: FormaLiquidacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.formaLiquidacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
