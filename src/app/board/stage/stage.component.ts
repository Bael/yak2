import {Component, Input, OnInit} from '@angular/core';
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

  tasks: Task[] = [];

  ngOnInit() {
  }

  createTask() {
    const task = new Task();
    task.name = 'new task #' + (this.tasks.length + 1);
    task.priority = 0;
    this.tasks.push(task);

  }
}
