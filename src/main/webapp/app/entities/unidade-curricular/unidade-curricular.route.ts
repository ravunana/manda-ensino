import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUnidadeCurricular, UnidadeCurricular } from 'app/shared/model/unidade-curricular.model';
import { UnidadeCurricularService } from './unidade-curricular.service';
import { UnidadeCurricularComponent } from './unidade-curricular.component';
import { UnidadeCurricularDetailComponent } from './unidade-curricular-detail.component';
import { UnidadeCurricularUpdateComponent } from './unidade-curricular-update.component';

@Injectable({ providedIn: 'root' })
export class UnidadeCurricularResolve implements Resolve<IUnidadeCurricular> {
  constructor(private service: UnidadeCurricularService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnidadeCurricular> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unidadeCurricular: HttpResponse<UnidadeCurricular>) => {
          if (unidadeCurricular.body) {
            return of(unidadeCurricular.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UnidadeCurricular());
  }
}

export const unidadeCurricularRoute: Routes = [
  {
    path: '',
    component: UnidadeCurricularComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.unidadeCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnidadeCurricularDetailComponent,
    resolve: {
      unidadeCurricular: UnidadeCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.unidadeCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnidadeCurricularUpdateComponent,
    resolve: {
      unidadeCurricular: UnidadeCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.unidadeCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnidadeCurricularUpdateComponent,
    resolve: {
      unidadeCurricular: UnidadeCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.unidadeCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
