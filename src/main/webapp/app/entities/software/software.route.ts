import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISoftware, Software } from 'app/shared/model/software.model';
import { SoftwareService } from './software.service';
import { SoftwareComponent } from './software.component';
import { SoftwareDetailComponent } from './software-detail.component';
import { SoftwareUpdateComponent } from './software-update.component';

@Injectable({ providedIn: 'root' })
export class SoftwareResolve implements Resolve<ISoftware> {
  constructor(private service: SoftwareService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISoftware> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((software: HttpResponse<Software>) => {
          if (software.body) {
            return of(software.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Software());
  }
}

export const softwareRoute: Routes = [
  {
    path: '',
    component: SoftwareComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SoftwareDetailComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SoftwareUpdateComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SoftwareUpdateComponent,
    resolve: {
      software: SoftwareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.software.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
