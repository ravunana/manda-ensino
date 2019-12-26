import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { SituacaoAcademicaComponent } from './situacao-academica.component';
import { SituacaoAcademicaDetailComponent } from './situacao-academica-detail.component';
import { SituacaoAcademicaUpdateComponent } from './situacao-academica-update.component';
import { SituacaoAcademicaDeleteDialogComponent } from './situacao-academica-delete-dialog.component';
import { situacaoAcademicaRoute } from './situacao-academica.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(situacaoAcademicaRoute)],
  declarations: [
    SituacaoAcademicaComponent,
    SituacaoAcademicaDetailComponent,
    SituacaoAcademicaUpdateComponent,
    SituacaoAcademicaDeleteDialogComponent
  ],
  entryComponents: [SituacaoAcademicaDeleteDialogComponent]
})
export class EnsinoSituacaoAcademicaModule {}
