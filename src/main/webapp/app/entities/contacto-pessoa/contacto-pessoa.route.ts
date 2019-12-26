import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactoPessoa, ContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { ContactoPessoaService } from './contacto-pessoa.service';
import { ContactoPessoaComponent } from './contacto-pessoa.component';
import { ContactoPessoaDetailComponent } from './contacto-pessoa-detail.component';
import { ContactoPessoaUpdateComponent } from './contacto-pessoa-update.component';

@Injectable({ providedIn: 'root' })
export class ContactoPessoaResolve implements Resolve<IContactoPessoa> {
  constructor(private service: ContactoPessoaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactoPessoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactoPessoa: HttpResponse<ContactoPessoa>) => {
          if (contactoPessoa.body) {
            return of(contactoPessoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactoPessoa());
  }
}

export const contactoPessoaRoute: Routes = [
  {
    path: '',
    component: ContactoPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContactoPessoaDetailComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContactoPessoaUpdateComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContactoPessoaUpdateComponent,
    resolve: {
      contactoPessoa: ContactoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
