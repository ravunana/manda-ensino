import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { FichaMedicaComponent } from './ficha-medica.component';
import { FichaMedicaDetailComponent } from './ficha-medica-detail.component';
import { FichaMedicaUpdateComponent } from './ficha-medica-update.component';
import { FichaMedicaDeleteDialogComponent } from './ficha-medica-delete-dialog.component';
import { fichaMedicaRoute } from './ficha-medica.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(fichaMedicaRoute)],
  declarations: [FichaMedicaComponent, FichaMedicaDetailComponent, FichaMedicaUpdateComponent, FichaMedicaDeleteDialogComponent],
  entryComponents: [FichaMedicaDeleteDialogComponent]
})
export class EnsinoFichaMedicaModule {}
