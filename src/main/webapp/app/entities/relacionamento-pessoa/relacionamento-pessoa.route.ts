import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRelacionamentoPessoa, RelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';
import { RelacionamentoPessoaService } from './relacionamento-pessoa.service';
import { RelacionamentoPessoaComponent } from './relacionamento-pessoa.component';
import { RelacionamentoPessoaDetailComponent } from './relacionamento-pessoa-detail.component';
import { RelacionamentoPessoaUpdateComponent } from './relacionamento-pessoa-update.component';

@Injectable({ providedIn: 'root' })
export class RelacionamentoPessoaResolve implements Resolve<IRelacionamentoPessoa> {
  constructor(private service: RelacionamentoPessoaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelacionamentoPessoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((relacionamentoPessoa: HttpResponse<RelacionamentoPessoa>) => {
          if (relacionamentoPessoa.body) {
            return of(relacionamentoPessoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RelacionamentoPessoa());
  }
}

export const relacionamentoPessoaRoute: Routes = [
  {
    path: '',
    component: RelacionamentoPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RelacionamentoPessoaDetailComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RelacionamentoPessoaUpdateComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RelacionamentoPessoaUpdateComponent,
    resolve: {
      relacionamentoPessoa: RelacionamentoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.relacionamentoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
