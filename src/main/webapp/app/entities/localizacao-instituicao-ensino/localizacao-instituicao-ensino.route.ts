import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocalizacaoInstituicaoEnsino, LocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';
import { LocalizacaoInstituicaoEnsinoService } from './localizacao-instituicao-ensino.service';
import { LocalizacaoInstituicaoEnsinoComponent } from './localizacao-instituicao-ensino.component';
import { LocalizacaoInstituicaoEnsinoDetailComponent } from './localizacao-instituicao-ensino-detail.component';
import { LocalizacaoInstituicaoEnsinoUpdateComponent } from './localizacao-instituicao-ensino-update.component';

@Injectable({ providedIn: 'root' })
export class LocalizacaoInstituicaoEnsinoResolve implements Resolve<ILocalizacaoInstituicaoEnsino> {
  constructor(private service: LocalizacaoInstituicaoEnsinoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalizacaoInstituicaoEnsino> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((localizacaoInstituicaoEnsino: HttpResponse<LocalizacaoInstituicaoEnsino>) => {
          if (localizacaoInstituicaoEnsino.body) {
            return of(localizacaoInstituicaoEnsino.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LocalizacaoInstituicaoEnsino());
  }
}

export const localizacaoInstituicaoEnsinoRoute: Routes = [
  {
    path: '',
    component: LocalizacaoInstituicaoEnsinoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.localizacaoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalizacaoInstituicaoEnsinoDetailComponent,
    resolve: {
      localizacaoInstituicaoEnsino: LocalizacaoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.localizacaoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalizacaoInstituicaoEnsinoUpdateComponent,
    resolve: {
      localizacaoInstituicaoEnsino: LocalizacaoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.localizacaoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalizacaoInstituicaoEnsinoUpdateComponent,
    resolve: {
      localizacaoInstituicaoEnsino: LocalizacaoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.localizacaoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
