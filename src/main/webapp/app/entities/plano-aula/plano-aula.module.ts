import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { PlanoAulaComponent } from './plano-aula.component';
import { PlanoAulaDetailComponent } from './plano-aula-detail.component';
import { PlanoAulaUpdateComponent } from './plano-aula-update.component';
import { PlanoAulaDeleteDialogComponent } from './plano-aula-delete-dialog.component';
import { planoAulaRoute } from './plano-aula.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(planoAulaRoute)],
  declarations: [PlanoAulaComponent, PlanoAulaDetailComponent, PlanoAulaUpdateComponent, PlanoAulaDeleteDialogComponent],
  entryComponents: [PlanoAulaDeleteDialogComponent]
})
export class EnsinoPlanoAulaModule {}
