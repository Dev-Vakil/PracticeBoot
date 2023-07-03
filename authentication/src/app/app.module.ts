import {MediaMatcher} from '@angular/cdk/layout';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import {MatRadioModule} from '@angular/material/radio';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NavBarComponent } from './components/user/nav-bar/nav-bar.component';
import { LoginComponent } from './components/user/login/login.component';
import { DashboardComponent } from './components/user/dashboard/dashboard.component';
import { SideBarComponent } from './components/user/side-bar/side-bar.component';
import { ProviderListComponent } from './components/user/provider-list/provider-list.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { AdminSignupComponent } from './components/admin/admin-signup/admin-signup.component';
import { UserComponent } from './components/user/user/user.component';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { AuthGuard } from './services/auth.guard';
import { AdminGuard } from './services/admin.guard';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    DashboardComponent,
    SideBarComponent,
    ProviderListComponent,
    AdminComponent,
    AdminSignupComponent,
    UserComponent,
    AdminLoginComponent,      
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatInputModule,    
    MatTableModule,
    MatSortModule,
    MatRadioModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
