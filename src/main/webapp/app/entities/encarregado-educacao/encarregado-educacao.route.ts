import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEncarregadoEducacao, EncarregadoEducacao } from 'app/shared/model/encarregado-educacao.model';
import { EncarregadoEducacaoService } from './encarregado-educacao.service';
import { EncarregadoEducacaoComponent } from './encarregado-educacao.component';
import { EncarregadoEducacaoDetailComponent } from './encarregado-educacao-detail.component';
import { EncarregadoEducacaoUpdateComponent } from './encarregado-educacao-update.component';

@Injectable({ providedIn: 'root' })
export class EncarregadoEducacaoResolve implements Resolve<IEncarregadoEducacao> {
  constructor(private service: EncarregadoEducacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncarregadoEducacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((encarregadoEducacao: HttpResponse<EncarregadoEducacao>) => {
          if (encarregadoEducacao.body) {
            return of(encarregadoEducacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EncarregadoEducacao());
  }
}

export const encarregadoEducacaoRoute: Routes = [
  {
    path: '',
    component: EncarregadoEducacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.encarregadoEducacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EncarregadoEducacaoDetailComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.encarregadoEducacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EncarregadoEducacaoUpdateComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.encarregadoEducacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EncarregadoEducacaoUpdateComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.encarregadoEducacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
