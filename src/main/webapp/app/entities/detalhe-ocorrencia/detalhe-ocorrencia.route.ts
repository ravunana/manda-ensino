import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDetalheOcorrencia, DetalheOcorrencia } from 'app/shared/model/detalhe-ocorrencia.model';
import { DetalheOcorrenciaService } from './detalhe-ocorrencia.service';
import { DetalheOcorrenciaComponent } from './detalhe-ocorrencia.component';
import { DetalheOcorrenciaDetailComponent } from './detalhe-ocorrencia-detail.component';
import { DetalheOcorrenciaUpdateComponent } from './detalhe-ocorrencia-update.component';

@Injectable({ providedIn: 'root' })
export class DetalheOcorrenciaResolve implements Resolve<IDetalheOcorrencia> {
  constructor(private service: DetalheOcorrenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalheOcorrencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((detalheOcorrencia: HttpResponse<DetalheOcorrencia>) => {
          if (detalheOcorrencia.body) {
            return of(detalheOcorrencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetalheOcorrencia());
  }
}

export const detalheOcorrenciaRoute: Routes = [
  {
    path: '',
    component: DetalheOcorrenciaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.detalheOcorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalheOcorrenciaDetailComponent,
    resolve: {
      detalheOcorrencia: DetalheOcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalheOcorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalheOcorrenciaUpdateComponent,
    resolve: {
      detalheOcorrencia: DetalheOcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalheOcorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalheOcorrenciaUpdateComponent,
    resolve: {
      detalheOcorrencia: DetalheOcorrenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.detalheOcorrencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
