import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactoInstituicaoEnsino, ContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';
import { ContactoInstituicaoEnsinoService } from './contacto-instituicao-ensino.service';
import { ContactoInstituicaoEnsinoComponent } from './contacto-instituicao-ensino.component';
import { ContactoInstituicaoEnsinoDetailComponent } from './contacto-instituicao-ensino-detail.component';
import { ContactoInstituicaoEnsinoUpdateComponent } from './contacto-instituicao-ensino-update.component';

@Injectable({ providedIn: 'root' })
export class ContactoInstituicaoEnsinoResolve implements Resolve<IContactoInstituicaoEnsino> {
  constructor(private service: ContactoInstituicaoEnsinoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactoInstituicaoEnsino> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactoInstituicaoEnsino: HttpResponse<ContactoInstituicaoEnsino>) => {
          if (contactoInstituicaoEnsino.body) {
            return of(contactoInstituicaoEnsino.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactoInstituicaoEnsino());
  }
}

export const contactoInstituicaoEnsinoRoute: Routes = [
  {
    path: '',
    component: ContactoInstituicaoEnsinoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.contactoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContactoInstituicaoEnsinoDetailComponent,
    resolve: {
      contactoInstituicaoEnsino: ContactoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContactoInstituicaoEnsinoUpdateComponent,
    resolve: {
      contactoInstituicaoEnsino: ContactoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContactoInstituicaoEnsinoUpdateComponent,
    resolve: {
      contactoInstituicaoEnsino: ContactoInstituicaoEnsinoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.contactoInstituicaoEnsino.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
