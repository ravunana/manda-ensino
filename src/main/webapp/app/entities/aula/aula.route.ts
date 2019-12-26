import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAula, Aula } from 'app/shared/model/aula.model';
import { AulaService } from './aula.service';
import { AulaComponent } from './aula.component';
import { AulaDetailComponent } from './aula-detail.component';
import { AulaUpdateComponent } from './aula-update.component';

@Injectable({ providedIn: 'root' })
export class AulaResolve implements Resolve<IAula> {
  constructor(private service: AulaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aula: HttpResponse<Aula>) => {
          if (aula.body) {
            return of(aula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aula());
  }
}

export const aulaRoute: Routes = [
  {
    path: '',
    component: AulaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.aula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AulaDetailComponent,
    resolve: {
      aula: AulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.aula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AulaUpdateComponent,
    resolve: {
      aula: AulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.aula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AulaUpdateComponent,
    resolve: {
      aula: AulaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.aula.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
