import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EnsinoSharedModule } from 'app/shared/shared.module';
import { EnsinoCoreModule } from 'app/core/core.module';
import { EnsinoAppRoutingModule } from './app-routing.module';
import { EnsinoHomeModule } from './home/home.module';
import { EnsinoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    BrowserModule,
    EnsinoSharedModule,
    EnsinoCoreModule,
    EnsinoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EnsinoEntityModule,
    EnsinoAppRoutingModule,
    BrowserAnimationsModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class EnsinoAppModule {}
