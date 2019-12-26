import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDetalhePagamento, DetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';
import { DetalhePagamentoService } from './detalhe-pagamento.service';
import { DetalhePagamentoComponent } from './detalhe-pagamento.component';
import { DetalhePagamentoDetailComponent } from './detalhe-pagamento-detail.component';
import { DetalhePagamentoUpdateComponent } from './detalhe-pagamento-update.component';

@Injectable({ providedIn: 'root' })
export class DetalhePagamentoResolve implements Resolve<IDetalhePagamento> {
  constructor(private service: DetalhePagamentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalhePagamento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((detalhePagamento: HttpResponse<DetalhePagamento>) => {
          if (detalhePagamento.body) {
            return of(detalhePagamento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetalhePagamento());
  }
}

export const detalhePagamentoRoute: Routes = [
  {
    path: '',
    component: DetalhePagamentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.detalhePagamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalhePagamentoDetailComponent,
    resolve: {
      detalhePagamento: DetalhePagamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalhePagamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalhePagamentoUpdateComponent,
    resolve: {
      detalhePagamento: DetalhePagamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalhePagamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalhePagamentoUpdateComponent,
    resolve: {
      detalhePagamento: DetalhePagamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalhePagamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
