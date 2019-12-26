import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { UnidadeCurricularComponent } from './unidade-curricular.component';
import { UnidadeCurricularDetailComponent } from './unidade-curricular-detail.component';
import { UnidadeCurricularUpdateComponent } from './unidade-curricular-update.component';
import { UnidadeCurricularDeleteDialogComponent } from './unidade-curricular-delete-dialog.component';
import { unidadeCurricularRoute } from './unidade-curricular.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(unidadeCurricularRoute)],
  declarations: [
    UnidadeCurricularComponent,
    UnidadeCurricularDetailComponent,
    UnidadeCurricularUpdateComponent,
    UnidadeCurricularDeleteDialogComponent
  ],
  entryComponents: [UnidadeCurricularDeleteDialogComponent]
})
export class EnsinoUnidadeCurricularModule {}
