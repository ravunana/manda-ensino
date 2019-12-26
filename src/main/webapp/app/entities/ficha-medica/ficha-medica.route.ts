import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFichaMedica, FichaMedica } from 'app/shared/model/ficha-medica.model';
import { FichaMedicaService } from './ficha-medica.service';
import { FichaMedicaComponent } from './ficha-medica.component';
import { FichaMedicaDetailComponent } from './ficha-medica-detail.component';
import { FichaMedicaUpdateComponent } from './ficha-medica-update.component';

@Injectable({ providedIn: 'root' })
export class FichaMedicaResolve implements Resolve<IFichaMedica> {
  constructor(private service: FichaMedicaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFichaMedica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fichaMedica: HttpResponse<FichaMedica>) => {
          if (fichaMedica.body) {
            return of(fichaMedica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FichaMedica());
  }
}

export const fichaMedicaRoute: Routes = [
  {
    path: '',
    component: FichaMedicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.fichaMedica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FichaMedicaDetailComponent,
    resolve: {
      fichaMedica: FichaMedicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.fichaMedica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FichaMedicaUpdateComponent,
    resolve: {
      fichaMedica: FichaMedicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.fichaMedica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FichaMedicaUpdateComponent,
    resolve: {
      fichaMedica: FichaMedicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.fichaMedica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
