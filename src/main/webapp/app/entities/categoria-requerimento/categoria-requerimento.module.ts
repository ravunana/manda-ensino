import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { CategoriaRequerimentoComponent } from './categoria-requerimento.component';
import { CategoriaRequerimentoDetailComponent } from './categoria-requerimento-detail.component';
import { CategoriaRequerimentoUpdateComponent } from './categoria-requerimento-update.component';
import { CategoriaRequerimentoDeleteDialogComponent } from './categoria-requerimento-delete-dialog.component';
import { categoriaRequerimentoRoute } from './categoria-requerimento.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(categoriaRequerimentoRoute)],
  declarations: [
    CategoriaRequerimentoComponent,
    CategoriaRequerimentoDetailComponent,
    CategoriaRequerimentoUpdateComponent,
    CategoriaRequerimentoDeleteDialogComponent
  ],
  entryComponents: [CategoriaRequerimentoDeleteDialogComponent]
})
export class EnsinoCategoriaRequerimentoModule {}
