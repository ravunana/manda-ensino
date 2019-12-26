import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILicencaSoftware, LicencaSoftware } from 'app/shared/model/licenca-software.model';
import { LicencaSoftwareService } from './licenca-software.service';
import { LicencaSoftwareComponent } from './licenca-software.component';
import { LicencaSoftwareDetailComponent } from './licenca-software-detail.component';
import { LicencaSoftwareUpdateComponent } from './licenca-software-update.component';

@Injectable({ providedIn: 'root' })
export class LicencaSoftwareResolve implements Resolve<ILicencaSoftware> {
  constructor(private service: LicencaSoftwareService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicencaSoftware> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((licencaSoftware: HttpResponse<LicencaSoftware>) => {
          if (licencaSoftware.body) {
            return of(licencaSoftware.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LicencaSoftware());
  }
}

export const licencaSoftwareRoute: Routes = [
  {
    path: '',
    component: LicencaSoftwareComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LicencaSoftwareDetailComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LicencaSoftwareUpdateComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LicencaSoftwareUpdateComponent,
    resolve: {
      licencaSoftware: LicencaSoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.licencaSoftware.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
