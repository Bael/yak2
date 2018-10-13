import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {BoardComponent} from './board/board.component';
import {StageComponent} from './board/stage/stage.component';
import {TaskComponent} from './board/task/task.component';
import {TaskService} from './services/task.service';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
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

const appRoutes: Routes = [
  {path: '', redirectTo: '/boards', pathMatch: 'full'},
  {path: 'boards', component: BoardListComponent},
  {path: 'board/:id', component: BoardComponent},
  {path: 'board-setup/:id', component: BoardSetupComponent},
  {path: 'task/:id', component: EditTaskComponent},
  {path: '**', component: PageNotFoundComponent}
];


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
    EditTaskComponent
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
    MatToolbarModule
  ],
  providers: [SettingsService, TaskService, StageService, BoardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
