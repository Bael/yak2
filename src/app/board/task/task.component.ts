import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from '../task';
import {Stage, Stages} from '../stage';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  @Input()
  task: Task;

  stages: Stage[] = Stages;
  constructor() { }

  ngOnInit() {
  }

  @Output()
  changeStage: EventEmitter<Task>;

  onSelected(stage: Stage) {
    this.task.stage = stage;
    this.changeStage.emit(this.task);
  }
}
