import {Component, OnDestroy, OnInit} from '@angular/core';
import {Board} from '../board/board';
import {Subscription} from 'rxjs/Subscription';
import {BoardService} from '../services/board.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {


  selectedBoard: Board;
  boards: Board[];

  private subscription: Subscription;

  constructor(private boardService: BoardService) {

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.subscription = this.boardService.getBoards().subscribe(boards => {
      this.boards = boards;
      if (this.boards.length > 0) {
        this.boardService.currentBoard.next(this.boards[0]);
      }
    });
  }


  onChangeBoard(event: any) {
    this.boardService.currentBoard.next(event);
  }

}
