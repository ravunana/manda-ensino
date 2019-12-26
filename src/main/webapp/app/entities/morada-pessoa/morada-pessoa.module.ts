import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { MoradaPessoaComponent } from './morada-pessoa.component';
import { MoradaPessoaDetailComponent } from './morada-pessoa-detail.component';
import { MoradaPessoaUpdateComponent } from './morada-pessoa-update.component';
import { MoradaPessoaDeleteDialogComponent } from './morada-pessoa-delete-dialog.component';
import { moradaPessoaRoute } from './morada-pessoa.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(moradaPessoaRoute)],
  declarations: [MoradaPessoaComponent, MoradaPessoaDetailComponent, MoradaPessoaUpdateComponent, MoradaPessoaDeleteDialogComponent],
  entryComponents: [MoradaPessoaDeleteDialogComponent]
})
export class EnsinoMoradaPessoaModule {}
