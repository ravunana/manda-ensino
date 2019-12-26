import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INota, Nota } from 'app/shared/model/nota.model';
import { NotaService } from './nota.service';
import { NotaComponent } from './nota.component';
import { NotaDetailComponent } from './nota-detail.component';
import { NotaUpdateComponent } from './nota-update.component';

@Injectable({ providedIn: 'root' })
export class NotaResolve implements Resolve<INota> {
  constructor(private service: NotaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INota> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nota: HttpResponse<Nota>) => {
          if (nota.body) {
            return of(nota.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nota());
  }
}

export const notaRoute: Routes = [
  {
    path: '',
    component: NotaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.nota.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotaDetailComponent,
    resolve: {
      nota: NotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.nota.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotaUpdateComponent,
    resolve: {
      nota: NotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.nota.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotaUpdateComponent,
    resolve: {
      nota: NotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.nota.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
