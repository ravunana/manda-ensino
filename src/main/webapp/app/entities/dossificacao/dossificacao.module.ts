import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DossificacaoComponent } from './dossificacao.component';
import { DossificacaoDetailComponent } from './dossificacao-detail.component';
import { DossificacaoUpdateComponent } from './dossificacao-update.component';
import { DossificacaoDeleteDialogComponent } from './dossificacao-delete-dialog.component';
import { dossificacaoRoute } from './dossificacao.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(dossificacaoRoute)],
  declarations: [DossificacaoComponent, DossificacaoDetailComponent, DossificacaoUpdateComponent, DossificacaoDeleteDialogComponent],
  entryComponents: [DossificacaoDeleteDialogComponent]
})
export class EnsinoDossificacaoModule {}
