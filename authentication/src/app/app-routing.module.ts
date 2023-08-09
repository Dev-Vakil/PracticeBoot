import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/user/login/login.component';
import { DashboardComponent } from './components/user/dashboard/dashboard.component';
import { AuthGuard, authGuard } from './services/auth.guard';
import { ProviderListComponent } from './components/admin/provider-list/provider-list.component';
import { AppComponent } from './app.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { UserComponent } from './components/user/user/user.component';
import { AdminSignupComponent } from './components/admin/admin-signup/admin-signup.component';
import { AdminGuard } from './services/admin.guard';
import { UserGuard } from './services/user.guard';
import { PayerListComponent } from './components/admin/payer-list/payer-list.component';
import { PayerProviderComponent } from './components/admin/payer-provider/payer-provider.component';
import { AssociatedPayersComponent } from './components/user/associated-payers/associated-payers.component';  
import { PricelistComponent } from './components/user/pricelist/pricelist.component';
import { UploadPricelistComponent } from './components/user/upload-pricelist/upload-pricelist.component';
import { ServicePricelistComponent } from './components/user/service-pricelist/service-pricelist.component';

const routes: Routes = [
  { 
    path: '', 
    component: AppComponent,
    children: [
      { path:'login',component:LoginComponent,pathMatch:'full'},
      { path: 'user', component: UserComponent, pathMatch: 'full'},
      { path: 'admin', component: AdminComponent, pathMatch: "full"},
      {path: '',redirectTo:'/login',pathMatch:'full'},               
    ]
  },
  { 
    path: 'user', 
    component: UserComponent,
    children: [           
      {path:'dashboard',component:DashboardComponent,pathMatch:'full', canActivate:[AuthGuard]},
      {path:'payers',component:AssociatedPayersComponent,pathMatch:'full', canActivate:[AuthGuard,UserGuard]},   
      {path:'upload-pricelist',component:UploadPricelistComponent,pathMatch:'full', canActivate:[AuthGuard,UserGuard]},  
      {path:'pricelist',component:PricelistComponent,pathMatch:'full', canActivate:[AuthGuard,UserGuard]},
      {path:'service-pricelist',component:ServicePricelistComponent, pathMatch:'full', canActivate:[AuthGuard,UserGuard]},   
      {path: '**', redirectTo:'dashboard'}
    ]
  },
  { 
    path: 'admin', 
    component: AdminComponent,
    children: [      
      {path:'register',component:AdminSignupComponent,pathMatch:'full'},
      {path:'provider-list',component:ProviderListComponent,pathMatch:'full',canActivate:[AdminGuard,AuthGuard]},
      {path:'payer-list',component:PayerListComponent,pathMatch:'full',canActivate:[AdminGuard,AuthGuard]},
      {path:'payer-provider', component:PayerProviderComponent,pathMatch:'full',canActivate:[AdminGuard,AuthGuard]},      
      {path: '**', redirectTo:'provider-list'}
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }