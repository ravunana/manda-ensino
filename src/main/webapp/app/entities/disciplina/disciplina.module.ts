import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EnsinoSharedModule } from 'app/shared/shared.module';
import { DisciplinaComponent } from './disciplina.component';
import { DisciplinaDetailComponent } from './disciplina-detail.component';
import { DisciplinaUpdateComponent } from './disciplina-update.component';
import { DisciplinaDeleteDialogComponent } from './disciplina-delete-dialog.component';
import { disciplinaRoute } from './disciplina.route';

@NgModule({
  imports: [EnsinoSharedModule, RouterModule.forChild(disciplinaRoute)],
  declarations: [DisciplinaComponent, DisciplinaDetailComponent, DisciplinaUpdateComponent, DisciplinaDeleteDialogComponent],
  entryComponents: [DisciplinaDeleteDialogComponent]
})
export class EnsinoDisciplinaModule {}
