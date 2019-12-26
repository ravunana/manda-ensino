import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DocumentacaoPessoaComponent } from './documentacao-pessoa.component';
import { DocumentacaoPessoaDetailComponent } from './documentacao-pessoa-detail.component';
import { DocumentacaoPessoaUpdateComponent } from './documentacao-pessoa-update.component';
import { DocumentacaoPessoaDeleteDialogComponent } from './documentacao-pessoa-delete-dialog.component';
import { documentacaoPessoaRoute } from './documentacao-pessoa.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(documentacaoPessoaRoute)],
  declarations: [
    DocumentacaoPessoaComponent,
    DocumentacaoPessoaDetailComponent,
    DocumentacaoPessoaUpdateComponent,
    DocumentacaoPessoaDeleteDialogComponent
  ],
  entryComponents: [DocumentacaoPessoaDeleteDialogComponent]
})
export class EnsinoDocumentacaoPessoaModule {}
