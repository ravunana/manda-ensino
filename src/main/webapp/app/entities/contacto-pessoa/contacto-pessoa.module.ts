import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { ContactoPessoaComponent } from './contacto-pessoa.component';
import { ContactoPessoaDetailComponent } from './contacto-pessoa-detail.component';
import { ContactoPessoaUpdateComponent } from './contacto-pessoa-update.component';
import { ContactoPessoaDeleteDialogComponent } from './contacto-pessoa-delete-dialog.component';
import { contactoPessoaRoute } from './contacto-pessoa.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(contactoPessoaRoute)],
  declarations: [
    ContactoPessoaComponent,
    ContactoPessoaDetailComponent,
    ContactoPessoaUpdateComponent,
    ContactoPessoaDeleteDialogComponent
  ],
  entryComponents: [ContactoPessoaDeleteDialogComponent]
})
export class EnsinoContactoPessoaModule {}
