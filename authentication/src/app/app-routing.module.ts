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
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { AdminGuard } from './services/admin.guard';
import { UserGuard } from './services/user.guard';
import { PayerListComponent } from './components/admin/payer-list/payer-list.component';
import { PayerProviderComponent } from './components/admin/payer-provider/payer-provider.component';
  
const routes: Routes = [
  { 
    path: '', 
    component: AppComponent,
    children: [
      { path: 'user', component: UserComponent, pathMatch: 'full'},
      { path: 'admin', component: AdminComponent, pathMatch: "full"},
      {path: '',redirectTo:'/user/login',pathMatch:'full'},
    ]
  },
  { 
    path: 'user', 
    component: UserComponent,
    children: [      
      {path:'login',component:LoginComponent,pathMatch:'full'},
      {path:'dashboard',component:DashboardComponent,pathMatch:'full', canActivate:[AuthGuard,UserGuard]},
    ]
  },
  { 
    path: 'admin', 
    component: AdminComponent,
    children: [
      {path:'login',component:AdminLoginComponent,pathMatch:'full'},
      {path:'register',component:AdminSignupComponent,pathMatch:'full'},
      {path:'provider-list',component:ProviderListComponent,pathMatch:'full',canActivate:[AuthGuard,AdminGuard]},
      {path:'payer-list',component:PayerListComponent,pathMatch:'full',canActivate:[AuthGuard,AdminGuard]},
      {path:'payer-provider', component:PayerProviderComponent,pathMatch:'full',canActivate:[AuthGuard,AdminGuard]}
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
