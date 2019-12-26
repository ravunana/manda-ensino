import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentacaoPessoa, DocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';
import { DocumentacaoPessoaService } from './documentacao-pessoa.service';
import { DocumentacaoPessoaComponent } from './documentacao-pessoa.component';
import { DocumentacaoPessoaDetailComponent } from './documentacao-pessoa-detail.component';
import { DocumentacaoPessoaUpdateComponent } from './documentacao-pessoa-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentacaoPessoaResolve implements Resolve<IDocumentacaoPessoa> {
  constructor(private service: DocumentacaoPessoaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentacaoPessoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentacaoPessoa: HttpResponse<DocumentacaoPessoa>) => {
          if (documentacaoPessoa.body) {
            return of(documentacaoPessoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentacaoPessoa());
  }
}

export const documentacaoPessoaRoute: Routes = [
  {
    path: '',
    component: DocumentacaoPessoaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.documentacaoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentacaoPessoaDetailComponent,
    resolve: {
      documentacaoPessoa: DocumentacaoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentacaoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentacaoPessoaUpdateComponent,
    resolve: {
      documentacaoPessoa: DocumentacaoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentacaoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentacaoPessoaUpdateComponent,
    resolve: {
      documentacaoPessoa: DocumentacaoPessoaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentacaoPessoa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
