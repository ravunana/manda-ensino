import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { InstituicaoEnsinoComponent } from './instituicao-ensino.component';
import { InstituicaoEnsinoDetailComponent } from './instituicao-ensino-detail.component';
import { InstituicaoEnsinoUpdateComponent } from './instituicao-ensino-update.component';
import { InstituicaoEnsinoDeleteDialogComponent } from './instituicao-ensino-delete-dialog.component';
import { instituicaoEnsinoRoute } from './instituicao-ensino.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(instituicaoEnsinoRoute)],
  declarations: [
    InstituicaoEnsinoComponent,
    InstituicaoEnsinoDetailComponent,
    InstituicaoEnsinoUpdateComponent,
    InstituicaoEnsinoDeleteDialogComponent
  ],
  entryComponents: [InstituicaoEnsinoDeleteDialogComponent]
})
export class EnsinoInstituicaoEnsinoModule {}
