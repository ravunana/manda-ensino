import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { MatriculaComponent } from './matricula.component';
import { MatriculaDetailComponent } from './matricula-detail.component';
import { MatriculaUpdateComponent } from './matricula-update.component';
import { MatriculaDeleteDialogComponent } from './matricula-delete-dialog.component';
import { matriculaRoute } from './matricula.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(matriculaRoute)],
  declarations: [MatriculaComponent, MatriculaDetailComponent, MatriculaUpdateComponent, MatriculaDeleteDialogComponent],
  entryComponents: [MatriculaDeleteDialogComponent]
})
export class EnsinoMatriculaModule {}
