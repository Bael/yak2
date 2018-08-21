import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Stage} from '../stage';
import {Task} from '../task';

@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrls: ['./stage.component.css']
})
export class StageComponent implements OnInit {

  constructor() { }

  @Input()
  stage: Stage;

  @Output()
  changeStage: EventEmitter<Task> = new EventEmitter<Task>();

  tasks: Task[] = [];

  ngOnInit() {
  }

  createTask() {
    const task = new Task();
    task.name = 'new task #' + (this.tasks.length + 1);
    task.priority = 0;
    this.tasks.push(task);
    this.sortTasks();

  }

  sortTasks() {
    this.tasks.sort((a, b) =>  b.priority - a.priority);
  }

  onChangeTaskState($event: Task) {
    this.tasks = this.tasks.filter(value => value !== $event);
    this.changeStage.emit($event);

  }

  onChangePriority($event: Task) {
    this.sortTasks();

  }
}
