import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { MatrizCurricularComponent } from './matriz-curricular.component';
import { MatrizCurricularDetailComponent } from './matriz-curricular-detail.component';
import { MatrizCurricularUpdateComponent } from './matriz-curricular-update.component';
import { MatrizCurricularDeleteDialogComponent } from './matriz-curricular-delete-dialog.component';
import { matrizCurricularRoute } from './matriz-curricular.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(matrizCurricularRoute)],
  declarations: [
    MatrizCurricularComponent,
    MatrizCurricularDetailComponent,
    MatrizCurricularUpdateComponent,
    MatrizCurricularDeleteDialogComponent
  ],
  entryComponents: [MatrizCurricularDeleteDialogComponent]
})
export class EnsinoMatrizCurricularModule {}
