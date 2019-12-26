import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { ContactoInstituicaoEnsinoComponent } from './contacto-instituicao-ensino.component';
import { ContactoInstituicaoEnsinoDetailComponent } from './contacto-instituicao-ensino-detail.component';
import { ContactoInstituicaoEnsinoUpdateComponent } from './contacto-instituicao-ensino-update.component';
import { ContactoInstituicaoEnsinoDeleteDialogComponent } from './contacto-instituicao-ensino-delete-dialog.component';
import { contactoInstituicaoEnsinoRoute } from './contacto-instituicao-ensino.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(contactoInstituicaoEnsinoRoute)],
  declarations: [
    ContactoInstituicaoEnsinoComponent,
    ContactoInstituicaoEnsinoDetailComponent,
    ContactoInstituicaoEnsinoUpdateComponent,
    ContactoInstituicaoEnsinoDeleteDialogComponent
  ],
  entryComponents: [ContactoInstituicaoEnsinoDeleteDialogComponent]
})
export class EnsinoContactoInstituicaoEnsinoModule {}
