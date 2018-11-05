import {Routes} from '@angular/router';
import {BoardListComponent} from './board-list/board-list.component';
import {BoardComponent} from './board/board.component';
import {BoardSetupComponent} from './board-setup/board-setup.component';
import {EditTaskComponent} from './board/edit-task/edit-task.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {SignupComponent} from './signup/signup.component';
import {AuthGuard} from './auth.guard';
import {UsersListComponent} from './users-list/users-list.component';


export const appRoutes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'boards', component: BoardListComponent, canActivate: [AuthGuard]},
  {path: 'users', component: UsersListComponent, canActivate: [AuthGuard]},
  {path: 'board/:id', component: BoardComponent, canActivate: [AuthGuard]},
  {path: 'board-setup/:id', component: BoardSetupComponent, canActivate: [AuthGuard]},
  {path: 'task/:id', component: EditTaskComponent, canActivate: [AuthGuard]},
  {path: '**', component: PageNotFoundComponent}
];
