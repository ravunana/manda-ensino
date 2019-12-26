import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatricula, Matricula } from 'app/shared/model/matricula.model';
import { MatriculaService } from './matricula.service';
import { MatriculaComponent } from './matricula.component';
import { MatriculaDetailComponent } from './matricula-detail.component';
import { MatriculaUpdateComponent } from './matricula-update.component';

@Injectable({ providedIn: 'root' })
export class MatriculaResolve implements Resolve<IMatricula> {
  constructor(private service: MatriculaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatricula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matricula: HttpResponse<Matricula>) => {
          if (matricula.body) {
            return of(matricula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Matricula());
  }
}

export const matriculaRoute: Routes = [
  {
    path: '',
    component: MatriculaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.matricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatriculaDetailComponent,
    resolve: {
      matricula: MatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatriculaUpdateComponent,
    resolve: {
      matricula: MatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatriculaUpdateComponent,
    resolve: {
      matricula: MatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.matricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
