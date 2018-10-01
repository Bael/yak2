import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stage} from './stage';
import {Task} from './task';
import {StageService} from '../services/stage.service';
import {Subscription} from 'rxjs/Subscription';
import {TaskService} from '../services/task.service';
import {Board} from './board';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnDestroy {
  board: Board;

  // stages: Stage[];
  private subscription: Subscription;

  constructor(private stageService: StageService, private taskService: TaskService) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit() {
  }


  moveTask(task: Task, index: number) {
    const stage: Stage = this.board.stages[index];
    task.stageId = stage.id;
    const subscr = this.taskService.updateTask(task).subscribe(
      () => {
        subscr.unsubscribe();
        stage.tasks.push(task);
      }
    );
  }

  loadStages(board: Board) {
    this.board = board;
    this.subscription = this.stageService.getStages(board.id).subscribe(value => this.board.stages = value,
      error1 => console.log(error1));
  }
}
