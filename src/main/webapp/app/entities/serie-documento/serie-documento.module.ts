import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { SerieDocumentoComponent } from './serie-documento.component';
import { SerieDocumentoDetailComponent } from './serie-documento-detail.component';
import { SerieDocumentoUpdateComponent } from './serie-documento-update.component';
import { SerieDocumentoDeleteDialogComponent } from './serie-documento-delete-dialog.component';
import { serieDocumentoRoute } from './serie-documento.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(serieDocumentoRoute)],
  declarations: [
    SerieDocumentoComponent,
    SerieDocumentoDetailComponent,
    SerieDocumentoUpdateComponent,
    SerieDocumentoDeleteDialogComponent
  ],
  entryComponents: [SerieDocumentoDeleteDialogComponent]
})
export class EnsinoSerieDocumentoModule {}
