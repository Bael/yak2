import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private loginService: LoginService) {
  }

  public username(): string {
    if (this.isLoggedIn()) {
      return this.loginService.userInfo.username;
    }
    return 'Guest';
  }

  ngOnInit() {
  }

  public isLoggedIn(): boolean {
    return this.loginService.authenticated === true;
  }

}
