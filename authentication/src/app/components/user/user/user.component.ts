import { animate, state, style, transition, trigger } from '@angular/animations';
import {MediaMatcher} from '@angular/cdk/layout';
import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SelectPayerIdModalComponent } from '../../modals/select-payer-id-modal/select-payer-id-modal.component';
import { filter } from 'rxjs';

interface PayerList {
  payerId: number;
  payerName: string;
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(-90deg)' })),
      transition('rotated => default', animate('500ms ease-out')),
      transition('default => rotated', animate('500ms ease-in'))
    ])
  ]
})
export class UserComponent {
  mobileQuery: MediaQueryList;
  
  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);
  rolesProvider : Boolean = false;
  roles = localStorage.getItem("roles")?.includes("USER");  
  loggedIn: Boolean = this.authService.isUserLoggedIn();
  state: string = 'default';
  payers !: PayerList;

  private _mobileQueryListener: () => void;
  
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private authService:AuthenticationService) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  rotate() {
      this.state = (this.state === 'default' ? 'rotated' : 'default');
  }
  
  logout(){
    this.loggedIn = false;
    this.authService.logout();
  }
  
  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
}
