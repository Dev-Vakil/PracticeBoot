import { animate, state, style, transition, trigger } from '@angular/animations';
import {MediaMatcher} from '@angular/cdk/layout';
import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SelectPayerIdModalComponent } from '../../modals/select-payer-id-modal/select-payer-id-modal.component';
import { filter } from 'rxjs';
import {faCaretDown} from '@fortawesome/free-solid-svg-icons';
import {faChevronDown} from '@fortawesome/free-solid-svg-icons';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(90deg)' })),
      transition('rotated => default', animate('300ms ease-out')),
      transition('default => rotated', animate('300ms ease-in'))
    ])
  ]
})
export class UserComponent {
  mobileQuery: MediaQueryList;
  
  dropDown = faCaretDown;

  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);
  rolesProvider : Boolean = false;
  roles = localStorage.getItem("roles")?.includes("USER");  
  loggedIn: Boolean = this.authService.isUserLoggedIn();
  state: string = 'default';
  state2: string = 'default';
  payerId!: number;
  payerName: string = "Unknown";

  private _mobileQueryListener: () => void;
  
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private authService:AuthenticationService, private router: Router, private dialog: MatDialog, private activatedRoute: ActivatedRoute) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);

    const dialogRef = this.dialog.open(SelectPayerIdModalComponent, {disableClose: true });    
    dialogRef.afterClosed().subscribe(
      data=> this.payerId =  data.payerId
    );
    dialogRef.afterClosed().subscribe(
      data=> this.payerName =  data.payerName
    );
    dialogRef.afterClosed().subscribe(
      data=> this.router.navigate(['/user/',this.payerId,'/dashboard'], { state: [this.payerId], relativeTo: this.activatedRoute })
    );
  }

  openDialog(){
    window.location.reload();
  }

  ngOnInit(){
  }

  rotate() {
      this.state = (this.state === 'default' ? 'rotated' : 'default');
  }

  rotate2() {
    this.state2 = (this.state2 === 'default' ? 'rotated' : 'default');
}
  
  logout(){
    this.loggedIn = false;
    this.authService.logout();
  }
  
  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  redirectUrl(url:string){
    this.router.navigate([url], { state: [this.payerId], relativeTo: this.activatedRoute })
  }
}
