import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMoradaPessoa, MoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { MoradaPessoaService } from './morada-pessoa.service';
import { MoradaPessoaComponent } from './morada-pessoa.component';
import { MoradaPessoaDetailComponent } from './morada-pessoa-detail.component';
import { MoradaPessoaUpdateComponent } from './morada-pessoa-update.component';

@Injectable({ providedIn: 'root' })
export class MoradaPessoaResolve implements Resolve<IMoradaPessoa> {
  constructor(private service: MoradaPessoaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoradaPessoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((moradaPessoa: HttpResponse<MoradaPessoa>) => {
          if (moradaPessoa.body) {
            return of(moradaPessoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoradaPessoa());
  }
}

export const moradaPessoaRoute: Routes = [
  {
    path: '',
    component: MoradaPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MoradaPessoaDetailComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MoradaPessoaUpdateComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MoradaPessoaUpdateComponent,
    resolve: {
      moradaPessoa: MoradaPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.moradaPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
