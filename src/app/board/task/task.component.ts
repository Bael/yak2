import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from '../task';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  @Input()
  task: Task;

  @Input()
  moveBackEnabled: boolean;
  @Input()
  moveAheadEnabled: boolean;

  @Output()
  moveAheadEvent: EventEmitter<Task> = new EventEmitter<Task>();
  @Output()
  moveBackEvent: EventEmitter<Task> = new EventEmitter<Task>();
  @Output()
  changePriority: EventEmitter<Task> = new EventEmitter<Task>();
  @Output()
  deleteTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();

  constructor() {
  }

  ngOnInit() {
  }

  onLikeTask() {
    this.task.priority++;
    this.changePriority.emit(this.task);
  }

  deleteTask() {
    this.deleteTaskEvent.emit(this.task);
  }

  moveAhead() {
    this.moveAheadEvent.emit(this.task);
  }


  getClass() {
    if (this.task.priority < 5) {
      return 'info';
    }

    if (this.task.priority < 10) {
      return 'warning';
    }

    return 'critical';
  }

  public toggleTimer() {

  }

  moveBack() {
    this.moveBackEvent.emit(this.task);
  }

  onEditTask() {

  }
}
