import { Component, OnInit } from '@angular/core';
import {LoginService} from '../services/login.service';
import {UserAccount} from '../user-account';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users: UserAccount[];

  constructor(private loginService: LoginService) { }


  ngOnInit() {
    this.loginService.getUsers().then(value => this.users = value);
  }

}
