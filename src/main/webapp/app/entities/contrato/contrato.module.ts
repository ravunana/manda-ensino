import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { ContratoComponent } from './contrato.component';
import { ContratoDetailComponent } from './contrato-detail.component';
import { ContratoUpdateComponent } from './contrato-update.component';
import { ContratoDeleteDialogComponent } from './contrato-delete-dialog.component';
import { contratoRoute } from './contrato.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(contratoRoute)],
  declarations: [ContratoComponent, ContratoDetailComponent, ContratoUpdateComponent, ContratoDeleteDialogComponent],
  entryComponents: [ContratoDeleteDialogComponent]
})
export class EnsinoContratoModule {}
