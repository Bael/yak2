import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginService} from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  taskAreLoading = false;

  constructor(private loginService: LoginService) {}

  public userName(): string {
    if (this.loginService.authenticated) {
      return this.loginService.userInfo.username;
    }
     return '';
  }
  public isLoggedIn(): boolean {
    return this.loginService.authenticated === true;
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout();

  }
}
