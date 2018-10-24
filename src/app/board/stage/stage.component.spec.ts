import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StageComponent } from './stage.component';
import {BoardComponent} from '../board.component';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
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

describe('StageComponent', () => {
  let component: StageComponent;
  let fixture: ComponentFixture<StageComponent>;

  beforeEach(async(() => {
  TestBed.configureTestingModule({
    declarations: [
      StageComponent
    ],
    imports: [
      BrowserModule,
      FormsModule,
      HttpClientModule, RouterModule.forRoot([]),
      BrowserModule,
      FormsModule,
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
    ], providers: [{provide: APP_BASE_HREF, useValue : '/' }]
  }).compileComponents();
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(StageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
