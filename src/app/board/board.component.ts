import {Component, OnDestroy, OnInit} from '@angular/core';
import {Stage} from './stage';
import {Task} from './task';
import {StageService} from '../services/stage.service';
import {Subscription} from 'rxjs/Subscription';
import {TaskService} from '../services/task.service';
import {Board} from './board';
import {BoardService} from '../services/board.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnDestroy {
  board: Board;

  // stages: Stage[];
  private subscription: Subscription;
  private sub: Subscription;

  constructor(private stageService: StageService,
              private taskService: TaskService,
              private boardService: BoardService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.sub.unsubscribe();
  }

  ngOnInit() {
    this.boardService.currentBoard.subscribe(value => this.loadStages(value));

    this.sub = this.activatedRoute.params.subscribe(value => {

      this.boardService.loadBoardById(value.id);
    });
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
