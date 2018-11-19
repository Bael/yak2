import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {BoardComponent} from './board/board.component';
import {StageComponent} from './board/stage/stage.component';
import {TaskComponent} from './board/task/task.component';
import {TaskService} from './services/task.service';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatProgressBarModule,
  MatSelectModule,
  MatToolbarModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SettingsService} from './services/settings.service';
import {StageService} from './services/stage.service';
import {BoardService} from './services/board.service';
import {RouterModule, Routes} from '@angular/router';
import {BoardSetupComponent} from './board-setup/board-setup.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {BoardListComponent} from './board-list/board-list.component';
import {EditTaskComponent} from './board/edit-task/edit-task.component';
import { HomeComponent } from './home/home.component';
import {appRoutes} from './routes';
import { LoginComponent } from './login/login.component';
import {LoginService} from './services/login.service';
import { SignupComponent } from './signup/signup.component';
import {AuthGuard} from './auth.guard';
import { UsersListComponent } from './users-list/users-list.component';


@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    StageComponent,
    TaskComponent,
    BoardSetupComponent,
    PageNotFoundComponent,
    DashboardComponent,
    BoardListComponent,
    EditTaskComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    UsersListComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true} // <-- debugging purposes only
    ),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatProgressBarModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatInputModule,
    MatListModule,
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [SettingsService, TaskService, StageService, BoardService, LoginService, AuthGuard, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
