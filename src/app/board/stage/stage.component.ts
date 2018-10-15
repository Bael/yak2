import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Stage} from '../stage';
import {Task} from '../task';
import {TaskService} from '../../services/task.service';
import {FormControl} from '@angular/forms';
import {Subject} from 'rxjs/Subject';
import {Subscription} from 'rxjs/Subscription';


@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrls: ['./stage.component.css']
})
export class StageComponent implements OnInit, OnDestroy {

  @Input()
  stage: Stage;
  @Output()
  moveAheadTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();

  @Output()
  moveBackTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();

  tasks: Task[] = [];
  newTaskText = '';
  @Input()
  moveBackEnabled: boolean;
  @Input()
  moveAheadEnabled: boolean;
  refreshStage = new Subject();
  taskNameFormControl = new FormControl('',);

  private subscription: Subscription;

  constructor(private tasksService: TaskService) {
  }

  ngOnInit() {

    this.loadTasks2();
  }


  loadTasks2() {
    this.subscription = this.tasksService
      .getTasksByStageId(this.stage.id)
      .subscribe(tasks => {
          this.stage.tasks = tasks;
          console.log('on get tasks');
        },
        error => console.log(error),
        () => console.log('done get tasks'));

  }


  loadTasks() {
    console.log('load tasks called!');
    this.refreshStage.next();

  }

  createTask() {
    const task = new Task();
    task.name = this.newTaskText;
    task.priority = 0;
    task.stageId = this.stage.id;

    const subscr = this.tasksService
      .createTask(task)
      .subscribe((createdTask) => {
          this.subscription.unsubscribe();
          this.stage.tasks.push(createdTask);
          subscr.unsubscribe();

        }
      );


    this.newTaskText = '';
    this.taskNameFormControl.reset();
    this.sortTasks();

  }

  sortTasks() {
    this.stage.tasks.sort((a, b) => b.priority - a.priority);
  }

  onChangePriority($event: Task) {
    this.sortTasks();

  }

  filterTasks(taskToHide: Task) {
    this.stage.tasks = this.stage.tasks.filter(task => task.id !== taskToHide.id);
  }

  onMoveBackEvent(task: Task) {
    this.moveBackTaskEvent.emit(task);
    this.filterTasks(task);
  }

  onMoveAheadEvent(task: Task) {
    this.moveAheadTaskEvent.emit(task);
    this.filterTasks(task);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  onDeleteTaskEvent(taskToDelete: Task) {

    const deleteSub = this.tasksService.deleteTask(taskToDelete).subscribe(() => {
      this.loadTasks2();
      // this.filterTasks(taskToDelete);
      // this.loadTasks();
      deleteSub.unsubscribe();
    });

  }
}
