import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { PessoaComponent } from './pessoa.component';
import { PessoaDetailComponent } from './pessoa-detail.component';
import { PessoaUpdateComponent } from './pessoa-update.component';
import { PessoaDeleteDialogComponent } from './pessoa-delete-dialog.component';
import { pessoaRoute } from './pessoa.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(pessoaRoute)],
  declarations: [PessoaComponent, PessoaDetailComponent, PessoaUpdateComponent, PessoaDeleteDialogComponent],
  entryComponents: [PessoaDeleteDialogComponent]
})
export class EnsinoPessoaModule {}
