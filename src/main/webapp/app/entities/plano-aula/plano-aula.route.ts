import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoAula, PlanoAula } from 'app/shared/model/plano-aula.model';
import { PlanoAulaService } from './plano-aula.service';
import { PlanoAulaComponent } from './plano-aula.component';
import { PlanoAulaDetailComponent } from './plano-aula-detail.component';
import { PlanoAulaUpdateComponent } from './plano-aula-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoAulaResolve implements Resolve<IPlanoAula> {
  constructor(private service: PlanoAulaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoAula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoAula: HttpResponse<PlanoAula>) => {
          if (planoAula.body) {
            return of(planoAula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoAula());
  }
}

export const planoAulaRoute: Routes = [
  {
    path: '',
    component: PlanoAulaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.planoAula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanoAulaDetailComponent,
    resolve: {
      planoAula: PlanoAulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoAula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanoAulaUpdateComponent,
    resolve: {
      planoAula: PlanoAulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoAula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanoAulaUpdateComponent,
    resolve: {
      planoAula: PlanoAulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoAula.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
