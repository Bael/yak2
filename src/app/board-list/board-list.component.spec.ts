import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BoardListComponent} from './board-list.component';
import {AppComponent} from '../app.component';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule, HttpHandler} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule, MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatProgressBarModule,
  MatSelectModule, MatToolbarModule
} from '@angular/material';
import {APP_BASE_HREF} from '@angular/common';
import {BoardService} from '../services/board.service';
import {SettingsService} from '../services/settings.service';
import {StageService} from '../services/stage.service';
import {BoardComponent} from '../board/board.component';
import {StageComponent} from '../board/stage/stage.component';
import {TaskComponent} from '../board/task/task.component';

describe('BoardListComponent', () => {
  let component: BoardListComponent;
  let fixture: ComponentFixture<BoardListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        BoardListComponent, BoardComponent, StageComponent, TaskComponent
      ],
      imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule, RouterModule.forRoot([]),

        BrowserAnimationsModule,
        MatProgressBarModule,
        MatButtonModule,
        MatCardModule,
        MatSelectModule,
        MatListModule,
        MatIconModule,
        MatToolbarModule,

        MatFormFieldModule,
        MatInputModule
      ], providers: [{provide: APP_BASE_HREF, useValue: '/'}, BoardService, SettingsService,
        StageService, HttpClient, HttpHandler]
    }).compileComponents();
  }))


  beforeEach(() => {
    fixture = TestBed.createComponent(BoardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
