import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAreaFormacao, AreaFormacao } from 'app/shared/model/area-formacao.model';
import { AreaFormacaoService } from './area-formacao.service';
import { AreaFormacaoComponent } from './area-formacao.component';
import { AreaFormacaoDetailComponent } from './area-formacao-detail.component';
import { AreaFormacaoUpdateComponent } from './area-formacao-update.component';

@Injectable({ providedIn: 'root' })
export class AreaFormacaoResolve implements Resolve<IAreaFormacao> {
  constructor(private service: AreaFormacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAreaFormacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((areaFormacao: HttpResponse<AreaFormacao>) => {
          if (areaFormacao.body) {
            return of(areaFormacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AreaFormacao());
  }
}

export const areaFormacaoRoute: Routes = [
  {
    path: '',
    component: AreaFormacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.areaFormacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AreaFormacaoDetailComponent,
    resolve: {
      areaFormacao: AreaFormacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.areaFormacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AreaFormacaoUpdateComponent,
    resolve: {
      areaFormacao: AreaFormacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.areaFormacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AreaFormacaoUpdateComponent,
    resolve: {
      areaFormacao: AreaFormacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.areaFormacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
