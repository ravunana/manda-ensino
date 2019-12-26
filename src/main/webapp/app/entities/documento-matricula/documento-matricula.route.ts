import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentoMatricula, DocumentoMatricula } from 'app/shared/model/documento-matricula.model';
import { DocumentoMatriculaService } from './documento-matricula.service';
import { DocumentoMatriculaComponent } from './documento-matricula.component';
import { DocumentoMatriculaDetailComponent } from './documento-matricula-detail.component';
import { DocumentoMatriculaUpdateComponent } from './documento-matricula-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentoMatriculaResolve implements Resolve<IDocumentoMatricula> {
  constructor(private service: DocumentoMatriculaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentoMatricula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentoMatricula: HttpResponse<DocumentoMatricula>) => {
          if (documentoMatricula.body) {
            return of(documentoMatricula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentoMatricula());
  }
}

export const documentoMatriculaRoute: Routes = [
  {
    path: '',
    component: DocumentoMatriculaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ensinoApp.documentoMatricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentoMatriculaDetailComponent,
    resolve: {
      documentoMatricula: DocumentoMatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentoMatricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentoMatriculaUpdateComponent,
    resolve: {
      documentoMatricula: DocumentoMatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentoMatricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentoMatriculaUpdateComponent,
    resolve: {
      documentoMatricula: DocumentoMatriculaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ensinoApp.documentoMatricula.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
