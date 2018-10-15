import {Component, OnDestroy, OnInit} from '@angular/core';
import {BoardService} from '../services/board.service';
import {Board} from '../board/board';
import {Subscription} from 'rxjs/Subscription';
import {Router} from '@angular/router';
import {StageService} from '../services/stage.service';

@Component({
  selector: 'app-board-list',
  templateUrl: './board-list.component.html',
  styleUrls: ['./board-list.component.css']
})
export class BoardListComponent implements OnInit, OnDestroy {
  boards: Board[];
  private sub: Subscription;


  constructor(private boardService: BoardService, private route: Router,
              private stageService: StageService) {
  }

  ngOnInit() {
    this.sub = this.boardService.boardsSource.subscribe(value => this.loadBoard(value));
    this.boardService.loadBoards();
  }

  loadBoard(board) {

    this.boards = board;

  }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  goToBoardSetup(board: Board): void {


    this.route.navigate(['board-setup', board.id]);


  }


  onSubmitNewBoard(value: string) {
    const board: Board = new Board();
    board.name = value;
    this.boardService.createBoard(board).then(value1 => this.boardService.loadBoards());
  }
}
