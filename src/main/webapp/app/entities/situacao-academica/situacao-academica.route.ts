import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISituacaoAcademica, SituacaoAcademica } from 'app/shared/model/situacao-academica.model';
import { SituacaoAcademicaService } from './situacao-academica.service';
import { SituacaoAcademicaComponent } from './situacao-academica.component';
import { SituacaoAcademicaDetailComponent } from './situacao-academica-detail.component';
import { SituacaoAcademicaUpdateComponent } from './situacao-academica-update.component';

@Injectable({ providedIn: 'root' })
export class SituacaoAcademicaResolve implements Resolve<ISituacaoAcademica> {
  constructor(private service: SituacaoAcademicaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISituacaoAcademica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((situacaoAcademica: HttpResponse<SituacaoAcademica>) => {
          if (situacaoAcademica.body) {
            return of(situacaoAcademica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SituacaoAcademica());
  }
}

export const situacaoAcademicaRoute: Routes = [
  {
    path: '',
    component: SituacaoAcademicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.situacaoAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SituacaoAcademicaDetailComponent,
    resolve: {
      situacaoAcademica: SituacaoAcademicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.situacaoAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SituacaoAcademicaUpdateComponent,
    resolve: {
      situacaoAcademica: SituacaoAcademicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.situacaoAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SituacaoAcademicaUpdateComponent,
    resolve: {
      situacaoAcademica: SituacaoAcademicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.situacaoAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
