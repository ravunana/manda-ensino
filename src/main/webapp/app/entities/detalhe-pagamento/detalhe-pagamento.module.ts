import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DetalhePagamentoComponent } from './detalhe-pagamento.component';
import { DetalhePagamentoDetailComponent } from './detalhe-pagamento-detail.component';
import { DetalhePagamentoUpdateComponent } from './detalhe-pagamento-update.component';
import { DetalhePagamentoDeleteDialogComponent } from './detalhe-pagamento-delete-dialog.component';
import { detalhePagamentoRoute } from './detalhe-pagamento.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(detalhePagamentoRoute)],
  declarations: [
    DetalhePagamentoComponent,
    DetalhePagamentoDetailComponent,
    DetalhePagamentoUpdateComponent,
    DetalhePagamentoDeleteDialogComponent
  ],
  entryComponents: [DetalhePagamentoDeleteDialogComponent]
})
export class EnsinoDetalhePagamentoModule {}
