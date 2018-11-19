import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';
import {Router} from '@angular/router';
import {UserAccount} from '../user-account';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  credentials: UserAccount = new UserAccount();

  result: string;
  error: string = null;
  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {

  }


  signup(username, password) {
    const credentials = new UserAccount();
    credentials.role = 'USER';
    credentials.username = username;
    credentials.password = password;


    this.loginService.signup(credentials).then(value => {

      console.log('signup done')
        this.result = 'You"ve successfully registered new user';
      this.error = null;
      this.router.navigateByUrl('/');
      },
        reason => {
          console.log('error while sign up' + JSON.stringify(reason));
          this.error = reason;
        }
    );
  }

}
