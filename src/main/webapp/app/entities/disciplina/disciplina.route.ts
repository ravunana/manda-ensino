import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDisciplina, Disciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from './disciplina.service';
import { DisciplinaComponent } from './disciplina.component';
import { DisciplinaDetailComponent } from './disciplina-detail.component';
import { DisciplinaUpdateComponent } from './disciplina-update.component';

@Injectable({ providedIn: 'root' })
export class DisciplinaResolve implements Resolve<IDisciplina> {
  constructor(private service: DisciplinaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisciplina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((disciplina: HttpResponse<Disciplina>) => {
          if (disciplina.body) {
            return of(disciplina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Disciplina());
  }
}

export const disciplinaRoute: Routes = [
  {
    path: '',
    component: DisciplinaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.disciplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DisciplinaDetailComponent,
    resolve: {
      disciplina: DisciplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.disciplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DisciplinaUpdateComponent,
    resolve: {
      disciplina: DisciplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.disciplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DisciplinaUpdateComponent,
    resolve: {
      disciplina: DisciplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.disciplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
