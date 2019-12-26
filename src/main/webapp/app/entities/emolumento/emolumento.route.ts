import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmolumento, Emolumento } from 'app/shared/model/emolumento.model';
import { EmolumentoService } from './emolumento.service';
import { EmolumentoComponent } from './emolumento.component';
import { EmolumentoDetailComponent } from './emolumento-detail.component';
import { EmolumentoUpdateComponent } from './emolumento-update.component';

@Injectable({ providedIn: 'root' })
export class EmolumentoResolve implements Resolve<IEmolumento> {
  constructor(private service: EmolumentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmolumento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emolumento: HttpResponse<Emolumento>) => {
          if (emolumento.body) {
            return of(emolumento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Emolumento());
  }
}

export const emolumentoRoute: Routes = [
  {
    path: '',
    component: EmolumentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.emolumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmolumentoDetailComponent,
    resolve: {
      emolumento: EmolumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.emolumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmolumentoUpdateComponent,
    resolve: {
      emolumento: EmolumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.emolumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmolumentoUpdateComponent,
    resolve: {
      emolumento: EmolumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.emolumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
