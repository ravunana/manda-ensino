import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatrizCurricular, MatrizCurricular } from 'app/shared/model/matriz-curricular.model';
import { MatrizCurricularService } from './matriz-curricular.service';
import { MatrizCurricularComponent } from './matriz-curricular.component';
import { MatrizCurricularDetailComponent } from './matriz-curricular-detail.component';
import { MatrizCurricularUpdateComponent } from './matriz-curricular-update.component';

@Injectable({ providedIn: 'root' })
export class MatrizCurricularResolve implements Resolve<IMatrizCurricular> {
  constructor(private service: MatrizCurricularService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatrizCurricular> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matrizCurricular: HttpResponse<MatrizCurricular>) => {
          if (matrizCurricular.body) {
            return of(matrizCurricular.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatrizCurricular());
  }
}

export const matrizCurricularRoute: Routes = [
  {
    path: '',
    component: MatrizCurricularComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.matrizCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatrizCurricularDetailComponent,
    resolve: {
      matrizCurricular: MatrizCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matrizCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatrizCurricularUpdateComponent,
    resolve: {
      matrizCurricular: MatrizCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matrizCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatrizCurricularUpdateComponent,
    resolve: {
      matrizCurricular: MatrizCurricularResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matrizCurricular.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
