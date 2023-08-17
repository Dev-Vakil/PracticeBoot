import { ErrorHandler, NgModule } from '@angular/core';
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
import { MatSortModule } from '@angular/material/sort';
import {MatRadioModule} from '@angular/material/radio';
import { MatTableModule } from '@angular/material/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './components/user/login/login.component';
import { DashboardComponent } from './components/user/dashboard/dashboard.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { AdminSignupComponent } from './components/admin/admin-signup/admin-signup.component';
import { UserComponent } from './components/user/user/user.component';
import {MatSelectModule} from '@angular/material/select';
import { ProviderListComponent } from './components/admin/provider-list/provider-list.component';
import { PayerListComponent } from './components/admin/payer-list/payer-list.component';
import { PayerProviderComponent } from './components/admin/payer-provider/payer-provider.component';
import { AssociatedPayersComponent } from './components/user/associated-payers/associated-payers.component';
import { PricelistComponent } from './components/user/pricelist/pricelist.component';
import {TooltipPosition, MatTooltipModule} from '@angular/material/tooltip';
import {MatRippleModule} from '@angular/material/core';
import { UploadPricelistComponent } from './components/user/upload-pricelist/upload-pricelist.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SelectPayerIdModalComponent } from './components/modals/select-payer-id-modal/select-payer-id-modal.component';
import {MatDialog, MAT_DIALOG_DATA, MatDialogModule} from '@angular/material/dialog';
import { ServicePricelistModalComponent } from './components/modals/service-pricelist-modal/service-pricelist-modal.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatExpansionModule} from '@angular/material/expansion';
import { GlobalErrorHandlerService } from './error-handler/global-error-handler.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    AdminComponent,
    AdminSignupComponent,
    UserComponent,
    ProviderListComponent,
    PayerListComponent,
    PayerProviderComponent,
    AssociatedPayersComponent,
    PricelistComponent,
    UploadPricelistComponent,
    SelectPayerIdModalComponent,
    ServicePricelistModalComponent  
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
    MatSortModule,
    MatRadioModule,
    MatTableModule,
    MatSelectModule,
    MatPaginatorModule,
    MatMenuModule,
    MatExpansionModule,
    MatDialogModule,
    MatTooltipModule,
    MatRippleModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [
    {provide:ErrorHandler, useClass:GlobalErrorHandlerService}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
