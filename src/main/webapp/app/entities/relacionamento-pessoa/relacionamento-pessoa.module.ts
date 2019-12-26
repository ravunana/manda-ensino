import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { RelacionamentoPessoaComponent } from './relacionamento-pessoa.component';
import { RelacionamentoPessoaDetailComponent } from './relacionamento-pessoa-detail.component';
import { RelacionamentoPessoaUpdateComponent } from './relacionamento-pessoa-update.component';
import { RelacionamentoPessoaDeleteDialogComponent } from './relacionamento-pessoa-delete-dialog.component';
import { relacionamentoPessoaRoute } from './relacionamento-pessoa.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(relacionamentoPessoaRoute)],
  declarations: [
    RelacionamentoPessoaComponent,
    RelacionamentoPessoaDetailComponent,
    RelacionamentoPessoaUpdateComponent,
    RelacionamentoPessoaDeleteDialogComponent
  ],
  entryComponents: [RelacionamentoPessoaDeleteDialogComponent]
})
export class EnsinoRelacionamentoPessoaModule {}
