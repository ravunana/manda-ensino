import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { LocalizacaoInstituicaoEnsinoComponent } from './localizacao-instituicao-ensino.component';
import { LocalizacaoInstituicaoEnsinoDetailComponent } from './localizacao-instituicao-ensino-detail.component';
import { LocalizacaoInstituicaoEnsinoUpdateComponent } from './localizacao-instituicao-ensino-update.component';
import { LocalizacaoInstituicaoEnsinoDeleteDialogComponent } from './localizacao-instituicao-ensino-delete-dialog.component';
import { localizacaoInstituicaoEnsinoRoute } from './localizacao-instituicao-ensino.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(localizacaoInstituicaoEnsinoRoute)],
  declarations: [
    LocalizacaoInstituicaoEnsinoComponent,
    LocalizacaoInstituicaoEnsinoDetailComponent,
    LocalizacaoInstituicaoEnsinoUpdateComponent,
    LocalizacaoInstituicaoEnsinoDeleteDialogComponent
  ],
  entryComponents: [LocalizacaoInstituicaoEnsinoDeleteDialogComponent]
})
export class EnsinoLocalizacaoInstituicaoEnsinoModule {}
