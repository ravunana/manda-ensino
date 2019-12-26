import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOcorrencia, Ocorrencia } from 'app/shared/model/ocorrencia.model';
import { OcorrenciaService } from './ocorrencia.service';
import { OcorrenciaComponent } from './ocorrencia.component';
import { OcorrenciaDetailComponent } from './ocorrencia-detail.component';
import { OcorrenciaUpdateComponent } from './ocorrencia-update.component';

@Injectable({ providedIn: 'root' })
export class OcorrenciaResolve implements Resolve<IOcorrencia> {
  constructor(private service: OcorrenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOcorrencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ocorrencia: HttpResponse<Ocorrencia>) => {
          if (ocorrencia.body) {
            return of(ocorrencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ocorrencia());
  }
}

export const ocorrenciaRoute: Routes = [
  {
    path: '',
    component: OcorrenciaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.ocorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OcorrenciaDetailComponent,
    resolve: {
      ocorrencia: OcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.ocorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OcorrenciaUpdateComponent,
    resolve: {
      ocorrencia: OcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.ocorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OcorrenciaUpdateComponent,
    resolve: {
      ocorrencia: OcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.ocorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
