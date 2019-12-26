import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { PlanoCurricularComponent } from './plano-curricular.component';
import { PlanoCurricularDetailComponent } from './plano-curricular-detail.component';
import { PlanoCurricularUpdateComponent } from './plano-curricular-update.component';
import { PlanoCurricularDeleteDialogComponent } from './plano-curricular-delete-dialog.component';
import { planoCurricularRoute } from './plano-curricular.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(planoCurricularRoute)],
  declarations: [
    PlanoCurricularComponent,
    PlanoCurricularDetailComponent,
    PlanoCurricularUpdateComponent,
    PlanoCurricularDeleteDialogComponent
  ],
  entryComponents: [PlanoCurricularDeleteDialogComponent]
})
export class EnsinoPlanoCurricularModule {}
