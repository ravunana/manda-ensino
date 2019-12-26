import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILookupItem, LookupItem } from 'app/shared/model/lookup-item.model';
import { LookupItemService } from './lookup-item.service';
import { LookupItemComponent } from './lookup-item.component';
import { LookupItemDetailComponent } from './lookup-item-detail.component';
import { LookupItemUpdateComponent } from './lookup-item-update.component';

@Injectable({ providedIn: 'root' })
export class LookupItemResolve implements Resolve<ILookupItem> {
  constructor(private service: LookupItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILookupItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lookupItem: HttpResponse<LookupItem>) => {
          if (lookupItem.body) {
            return of(lookupItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LookupItem());
  }
}

export const lookupItemRoute: Routes = [
  {
    path: '',
    component: LookupItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LookupItemDetailComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.lookupItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
