import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Board} from './board/board';
import {BoardService} from './services/board.service';
import {Subscription} from 'rxjs/Subscription';
import {BoardComponent} from './board/board.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  taskAreLoading = false;

  selectedBoard: Board;
  boards: Board[];
  @ViewChild('boardcomponent')
  boardComponent: BoardComponent;
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
        this.boardComponent.loadStages(this.boards[0]);
      }
    });
  }


  onChangeBoard(event: any) {
    this.boardComponent.loadStages(event);
  }
}
