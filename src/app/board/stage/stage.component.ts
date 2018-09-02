import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Stage} from '../stage';
import {Task} from '../task';
import {TaskService} from '../../services/task.service';

@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrls: ['./stage.component.css']
})
export class StageComponent implements OnInit {

  constructor(private tasksService: TaskService) {}

  @Input()
  stage: Stage;

  @Output()
  changeStage: EventEmitter<Task> = new EventEmitter<Task>();

  tasks: Task[] = [];

  ngOnInit() {
    this.tasksService.getTasks().subscribe(tasks => this.tasks = tasks as Task[],
      error => console.log(error),
      () => console.log('done'));
  }

  createTask() {
    const task = new Task();
    task.name = 'new task #' + (this.tasks.length + 1);
    task.priority = 0;
    this.tasksService.createTask(task).subscribe(value => this.tasks.push(value as Task));

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
