import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { RequerimentoComponent } from './requerimento.component';
import { RequerimentoDetailComponent } from './requerimento-detail.component';
import { RequerimentoUpdateComponent } from './requerimento-update.component';
import { RequerimentoDeleteDialogComponent } from './requerimento-delete-dialog.component';
import { requerimentoRoute } from './requerimento.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(requerimentoRoute)],
  declarations: [RequerimentoComponent, RequerimentoDetailComponent, RequerimentoUpdateComponent, RequerimentoDeleteDialogComponent],
  entryComponents: [RequerimentoDeleteDialogComponent]
})
export class EnsinoRequerimentoModule {}
