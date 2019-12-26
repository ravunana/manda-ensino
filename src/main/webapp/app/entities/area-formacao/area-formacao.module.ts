import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { AreaFormacaoComponent } from './area-formacao.component';
import { AreaFormacaoDetailComponent } from './area-formacao-detail.component';
import { AreaFormacaoUpdateComponent } from './area-formacao-update.component';
import { AreaFormacaoDeleteDialogComponent } from './area-formacao-delete-dialog.component';
import { areaFormacaoRoute } from './area-formacao.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(areaFormacaoRoute)],
  declarations: [AreaFormacaoComponent, AreaFormacaoDetailComponent, AreaFormacaoUpdateComponent, AreaFormacaoDeleteDialogComponent],
  entryComponents: [AreaFormacaoDeleteDialogComponent]
})
export class EnsinoAreaFormacaoModule {}
