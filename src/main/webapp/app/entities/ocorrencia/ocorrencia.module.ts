import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { OcorrenciaComponent } from './ocorrencia.component';
import { OcorrenciaDetailComponent } from './ocorrencia-detail.component';
import { OcorrenciaUpdateComponent } from './ocorrencia-update.component';
import { OcorrenciaDeleteDialogComponent } from './ocorrencia-delete-dialog.component';
import { ocorrenciaRoute } from './ocorrencia.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(ocorrenciaRoute)],
  declarations: [OcorrenciaComponent, OcorrenciaDetailComponent, OcorrenciaUpdateComponent, OcorrenciaDeleteDialogComponent],
  entryComponents: [OcorrenciaDeleteDialogComponent]
})
export class EnsinoOcorrenciaModule {}
