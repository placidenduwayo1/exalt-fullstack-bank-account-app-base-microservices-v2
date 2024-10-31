import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenubarModule } from 'primeng/menubar';
import { CardModule } from 'primeng/card';
import { NgOptimizedImage, registerLocaleData } from '@angular/common';
import { SplitterModule } from "primeng/splitter";
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { SessionComponent } from './pages/session/session.component';
import { KataMenuComponent } from './pages/menu/kata-menu.component';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import localeFr from '@angular/common/locales/fr';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';
import { appHttpInterceptor } from './shared/services/authentication/request-interceptor';
import { TabViewModule } from 'primeng/tabview';
import { SidebarModule } from 'primeng/sidebar';
import { TableModule } from 'primeng/table';
import { MenuAccueilComponent } from './menu-accueil/menu-accueil.component';
import { Accueil1Component } from './accueil/accueil1/accueil1.component';
import { Accueil2Component } from './accueil/accueil2/accueil2.component';
import { Accueil3Component } from './accueil/accueil3/accueil3.component';

registerLocaleData(localeFr);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SessionComponent,
    KataMenuComponent,
    Accueil1Component,
    Accueil2Component,
    Accueil3Component,
    MenuAccueilComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    CardModule,
    NgOptimizedImage,
    SplitterModule,
    ButtonModule,
    FormsModule,
    PasswordModule,
    ReactiveFormsModule,
    InputTextModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ConfirmDialogModule,
    MessagesModule,
    TabViewModule,
    SidebarModule,
    TableModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    ConfirmationService, MessageService,
    provideHttpClient(withInterceptors([appHttpInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
