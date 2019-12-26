import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { NotaComponent } from './nota.component';
import { NotaDetailComponent } from './nota-detail.component';
import { NotaUpdateComponent } from './nota-update.component';
import { NotaDeleteDialogComponent } from './nota-delete-dialog.component';
import { notaRoute } from './nota.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(notaRoute)],
  declarations: [NotaComponent, NotaDetailComponent, NotaUpdateComponent, NotaDeleteDialogComponent],
  entryComponents: [NotaDeleteDialogComponent]
})
export class EnsinoNotaModule {}
