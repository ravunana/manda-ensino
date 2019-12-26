import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { CategoriaValiacaoComponent } from './categoria-valiacao.component';
import { CategoriaValiacaoDetailComponent } from './categoria-valiacao-detail.component';
import { CategoriaValiacaoUpdateComponent } from './categoria-valiacao-update.component';
import { CategoriaValiacaoDeleteDialogComponent } from './categoria-valiacao-delete-dialog.component';
import { categoriaValiacaoRoute } from './categoria-valiacao.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(categoriaValiacaoRoute)],
  declarations: [
    CategoriaValiacaoComponent,
    CategoriaValiacaoDetailComponent,
    CategoriaValiacaoUpdateComponent,
    CategoriaValiacaoDeleteDialogComponent
  ],
  entryComponents: [CategoriaValiacaoDeleteDialogComponent]
})
export class EnsinoCategoriaValiacaoModule {}
