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
  changeStage: EventEmitter<Task> = new EventEmitter<Task>();

  @Output()
  changePriority: EventEmitter<Task> = new EventEmitter<Task>();


  onSelected(stageName: string) {
    this.task.stage = this.stages.find(value => value.name === stageName);
    this.changeStage.emit(this.task);
  }

  onLikeTask() {
    this.task.priority++;
    this.changePriority.emit(this.task);
  }
}
