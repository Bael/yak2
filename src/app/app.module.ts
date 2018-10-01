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


@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    StageComponent,
    TaskComponent
  ],
  imports: [

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
    MatToolbarModule
  ],
  providers: [SettingsService, TaskService, StageService, BoardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
