import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDossificacao, Dossificacao } from 'app/shared/model/dossificacao.model';
import { DossificacaoService } from './dossificacao.service';
import { DossificacaoComponent } from './dossificacao.component';
import { DossificacaoDetailComponent } from './dossificacao-detail.component';
import { DossificacaoUpdateComponent } from './dossificacao-update.component';

@Injectable({ providedIn: 'root' })
export class DossificacaoResolve implements Resolve<IDossificacao> {
  constructor(private service: DossificacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDossificacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dossificacao: HttpResponse<Dossificacao>) => {
          if (dossificacao.body) {
            return of(dossificacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dossificacao());
  }
}

export const dossificacaoRoute: Routes = [
  {
    path: '',
    component: DossificacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.dossificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DossificacaoDetailComponent,
    resolve: {
      dossificacao: DossificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.dossificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DossificacaoUpdateComponent,
    resolve: {
      dossificacao: DossificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.dossificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DossificacaoUpdateComponent,
    resolve: {
      dossificacao: DossificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.dossificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
