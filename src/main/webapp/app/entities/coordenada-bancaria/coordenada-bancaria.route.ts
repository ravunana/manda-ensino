import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICoordenadaBancaria, CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';
import { CoordenadaBancariaComponent } from './coordenada-bancaria.component';
import { CoordenadaBancariaDetailComponent } from './coordenada-bancaria-detail.component';
import { CoordenadaBancariaUpdateComponent } from './coordenada-bancaria-update.component';

@Injectable({ providedIn: 'root' })
export class CoordenadaBancariaResolve implements Resolve<ICoordenadaBancaria> {
  constructor(private service: CoordenadaBancariaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoordenadaBancaria> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((coordenadaBancaria: HttpResponse<CoordenadaBancaria>) => {
          if (coordenadaBancaria.body) {
            return of(coordenadaBancaria.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CoordenadaBancaria());
  }
}

export const coordenadaBancariaRoute: Routes = [
  {
    path: '',
    component: CoordenadaBancariaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoordenadaBancariaDetailComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoordenadaBancariaUpdateComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoordenadaBancariaUpdateComponent,
    resolve: {
      coordenadaBancaria: CoordenadaBancariaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.coordenadaBancaria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
