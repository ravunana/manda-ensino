import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { CriterioAvaliacaoComponent } from './criterio-avaliacao.component';
import { CriterioAvaliacaoDetailComponent } from './criterio-avaliacao-detail.component';
import { CriterioAvaliacaoUpdateComponent } from './criterio-avaliacao-update.component';
import { CriterioAvaliacaoDeleteDialogComponent } from './criterio-avaliacao-delete-dialog.component';
import { criterioAvaliacaoRoute } from './criterio-avaliacao.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(criterioAvaliacaoRoute)],
  declarations: [
    CriterioAvaliacaoComponent,
    CriterioAvaliacaoDetailComponent,
    CriterioAvaliacaoUpdateComponent,
    CriterioAvaliacaoDeleteDialogComponent
  ],
  entryComponents: [CriterioAvaliacaoDeleteDialogComponent]
})
export class EnsinoCriterioAvaliacaoModule {}
