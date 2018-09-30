import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stage} from './stage';
import {Task} from './task';
import {StageService} from '../services/stage.service';
import {Subscription} from 'rxjs/Subscription';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnDestroy {
  stages: Stage[];
  private subscription: Subscription;

  constructor(private stageService: StageService, private taskService: TaskService) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit() {
    this.subscription = this.stageService.getStages().subscribe(value => this.stages = value,
      error1 => console.log(error1));
  }


  moveTask(task: Task, index: number) {
    const stage: Stage = this.stages[index];
    task.stageId = stage.id;
    const subscr = this.taskService.updateTask(task).subscribe(
      () => {
        subscr.unsubscribe();
        stage.tasks.push(task);
      }
    );
  }

}
