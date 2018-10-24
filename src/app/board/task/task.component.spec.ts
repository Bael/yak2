import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TaskComponent} from './task.component';
import {DebugElement} from '@angular/core';
import {TaskService} from '../../services/task.service';
import {Observable} from 'rxjs/Observable';
import {Task} from '../task';
import {of} from 'rxjs/observable/of';
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

describe('TaskComponent', () => {
  let component: TaskComponent;
  let fixture: ComponentFixture<TaskComponent>;
  let debugElement: DebugElement;
  let service: TaskService;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TaskComponent],
      imports: [BrowserModule,
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
        MatInputModule],
      providers: [{provide: TaskService, useClass: TaskServiceStub} , {provide: APP_BASE_HREF, useValue : '/' }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    service = debugElement.injector.get(TaskService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create task service', () => {
    expect(service).toBeTruthy();
  });


  it('should call task service on get tasks', () => {
    const mockTask: Task = new Task();
    spyOn(service, 'deleteTask').and.callThrough();
    component.task = mockTask;
    component.deleteTask();
    expect(service.updateTask).toHaveBeenCalledWith(mockTask);
  });

});

export class TaskServiceStub {
  getTasks(): Observable<Task[]> {
    return of([new Task()]);
  }

  getTasksByStageId(stageId: Number): Observable<Task[]> {
    return of([new Task()]);
  }

  createTask(task: Task): Observable<Task> {
    return of(new Task());
  }

  updateTask(task: Task): Observable<Task> {
    return of(new Task());
  }

  deleteTask(task: Task): Observable<Task> {
    return of(new Task());
  }

  findTask(id: number): Observable<Task> {
    return of(new Task());
  }

}
