import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { EncarregadoEducacaoComponent } from './encarregado-educacao.component';
import { EncarregadoEducacaoDetailComponent } from './encarregado-educacao-detail.component';
import { EncarregadoEducacaoUpdateComponent } from './encarregado-educacao-update.component';
import { EncarregadoEducacaoDeleteDialogComponent } from './encarregado-educacao-delete-dialog.component';
import { encarregadoEducacaoRoute } from './encarregado-educacao.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(encarregadoEducacaoRoute)],
  declarations: [
    EncarregadoEducacaoComponent,
    EncarregadoEducacaoDetailComponent,
    EncarregadoEducacaoUpdateComponent,
    EncarregadoEducacaoDeleteDialogComponent
  ],
  entryComponents: [EncarregadoEducacaoDeleteDialogComponent]
})
export class EnsinoEncarregadoEducacaoModule {}
