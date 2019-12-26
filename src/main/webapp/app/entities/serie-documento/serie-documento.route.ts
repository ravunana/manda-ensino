import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISerieDocumento, SerieDocumento } from 'app/shared/model/serie-documento.model';
import { SerieDocumentoService } from './serie-documento.service';
import { SerieDocumentoComponent } from './serie-documento.component';
import { SerieDocumentoDetailComponent } from './serie-documento-detail.component';
import { SerieDocumentoUpdateComponent } from './serie-documento-update.component';

@Injectable({ providedIn: 'root' })
export class SerieDocumentoResolve implements Resolve<ISerieDocumento> {
  constructor(private service: SerieDocumentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISerieDocumento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serieDocumento: HttpResponse<SerieDocumento>) => {
          if (serieDocumento.body) {
            return of(serieDocumento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SerieDocumento());
  }
}

export const serieDocumentoRoute: Routes = [
  {
    path: '',
    component: SerieDocumentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SerieDocumentoDetailComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.serieDocumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
