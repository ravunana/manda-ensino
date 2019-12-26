import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DocumentoMatriculaComponent } from './documento-matricula.component';
import { DocumentoMatriculaDetailComponent } from './documento-matricula-detail.component';
import { DocumentoMatriculaUpdateComponent } from './documento-matricula-update.component';
import { DocumentoMatriculaDeleteDialogComponent } from './documento-matricula-delete-dialog.component';
import { documentoMatriculaRoute } from './documento-matricula.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(documentoMatriculaRoute)],
  declarations: [
    DocumentoMatriculaComponent,
    DocumentoMatriculaDetailComponent,
    DocumentoMatriculaUpdateComponent,
    DocumentoMatriculaDeleteDialogComponent
  ],
  entryComponents: [DocumentoMatriculaDeleteDialogComponent]
})
export class EnsinoDocumentoMatriculaModule {}
