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
  private subscription: Subscription;
  private sub: Subscription;

  constructor(private stageService: StageService,
              private taskService: TaskService,
              private boardService: BoardService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnDestroy(): void {
  }

  ngOnInit() {
    this.boardService.currentBoard.subscribe(value => this.board = value);
    this.sub = this.activatedRoute.params.subscribe(value => {
      this.boardService.loadBoardById(value.id);
    });
  }


  moveTask(task: Task, index: number) {
    const stage: Stage = this.board.stages[index];
    task.stageId = stage.id;
    stage.tasks.push(task);
    this.boardService.updateBoard(this.board).then(value => console.log(value));
  }

}
