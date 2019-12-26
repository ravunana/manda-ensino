import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DetalheOcorrenciaComponent } from './detalhe-ocorrencia.component';
import { DetalheOcorrenciaDetailComponent } from './detalhe-ocorrencia-detail.component';
import { DetalheOcorrenciaUpdateComponent } from './detalhe-ocorrencia-update.component';
import { DetalheOcorrenciaDeleteDialogComponent } from './detalhe-ocorrencia-delete-dialog.component';
import { detalheOcorrenciaRoute } from './detalhe-ocorrencia.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(detalheOcorrenciaRoute)],
  declarations: [
    DetalheOcorrenciaComponent,
    DetalheOcorrenciaDetailComponent,
    DetalheOcorrenciaUpdateComponent,
    DetalheOcorrenciaDeleteDialogComponent
  ],
  entryComponents: [DetalheOcorrenciaDeleteDialogComponent]
})
export class EnsinoDetalheOcorrenciaModule {}
