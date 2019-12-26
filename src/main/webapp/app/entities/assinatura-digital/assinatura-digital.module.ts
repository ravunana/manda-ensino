import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { AssinaturaDigitalComponent } from './assinatura-digital.component';
import { AssinaturaDigitalDetailComponent } from './assinatura-digital-detail.component';
import { AssinaturaDigitalUpdateComponent } from './assinatura-digital-update.component';
import { AssinaturaDigitalDeleteDialogComponent } from './assinatura-digital-delete-dialog.component';
import { assinaturaDigitalRoute } from './assinatura-digital.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(assinaturaDigitalRoute)],
  declarations: [
    AssinaturaDigitalComponent,
    AssinaturaDigitalDetailComponent,
    AssinaturaDigitalUpdateComponent,
    AssinaturaDigitalDeleteDialogComponent
  ],
  entryComponents: [AssinaturaDigitalDeleteDialogComponent]
})
export class EnsinoAssinaturaDigitalModule {}
