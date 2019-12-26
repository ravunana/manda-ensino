import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoCurricular, PlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { PlanoCurricularService } from './plano-curricular.service';
import { PlanoCurricularComponent } from './plano-curricular.component';
import { PlanoCurricularDetailComponent } from './plano-curricular-detail.component';
import { PlanoCurricularUpdateComponent } from './plano-curricular-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoCurricularResolve implements Resolve<IPlanoCurricular> {
  constructor(private service: PlanoCurricularService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoCurricular> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoCurricular: HttpResponse<PlanoCurricular>) => {
          if (planoCurricular.body) {
            return of(planoCurricular.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoCurricular());
  }
}

export const planoCurricularRoute: Routes = [
  {
    path: '',
    component: PlanoCurricularComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.planoCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanoCurricularDetailComponent,
    resolve: {
      planoCurricular: PlanoCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanoCurricularUpdateComponent,
    resolve: {
      planoCurricular: PlanoCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanoCurricularUpdateComponent,
    resolve: {
      planoCurricular: PlanoCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.planoCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
