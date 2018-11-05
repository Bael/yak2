import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';
import {Router} from '@angular/router';
import {UserAccount} from '../user-account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {username: '', password: ''};

  error = '';
  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
  }

  login(username, password) {
    this.credentials.username = username;
    this.credentials.password = password;

    this.loginService.login(this.credentials, () => {
      this.router.navigateByUrl('/boards');
    }, () => this.error = 'Please try again');
    return false;
  }

}
