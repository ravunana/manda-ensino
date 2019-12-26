import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssinaturaDigital, AssinaturaDigital } from 'app/shared/model/assinatura-digital.model';
import { AssinaturaDigitalService } from './assinatura-digital.service';
import { AssinaturaDigitalComponent } from './assinatura-digital.component';
import { AssinaturaDigitalDetailComponent } from './assinatura-digital-detail.component';
import { AssinaturaDigitalUpdateComponent } from './assinatura-digital-update.component';

@Injectable({ providedIn: 'root' })
export class AssinaturaDigitalResolve implements Resolve<IAssinaturaDigital> {
  constructor(private service: AssinaturaDigitalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssinaturaDigital> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((assinaturaDigital: HttpResponse<AssinaturaDigital>) => {
          if (assinaturaDigital.body) {
            return of(assinaturaDigital.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssinaturaDigital());
  }
}

export const assinaturaDigitalRoute: Routes = [
  {
    path: '',
    component: AssinaturaDigitalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.assinaturaDigital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssinaturaDigitalDetailComponent,
    resolve: {
      assinaturaDigital: AssinaturaDigitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.assinaturaDigital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssinaturaDigitalUpdateComponent,
    resolve: {
      assinaturaDigital: AssinaturaDigitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.assinaturaDigital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssinaturaDigitalUpdateComponent,
    resolve: {
      assinaturaDigital: AssinaturaDigitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.assinaturaDigital.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
