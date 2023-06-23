import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthGuard, authGuard } from './services/auth.guard';
import { AuthenticatedComponent } from './components/authenticated/authenticated.component';
  
const routes: Routes = [
  {path:'',redirectTo: '/login',pathMatch:'full'},
  {path:'login',component:LoginComponent,pathMatch:'full'},
  {path:'register',component:RegisterComponent,pathMatch:'full'},
  {path:'authenticated',component:AuthenticatedComponent,pathMatch:'full'},
  {path:'dashboard',component:DashboardComponent,pathMatch:'full',canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
