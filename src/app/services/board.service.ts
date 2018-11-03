import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SettingsService} from './settings.service';
import {Observable} from 'rxjs/Observable';
import {Board} from '../board/board';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class BoardService {

  currentBoard: Subject<Board> = new Subject<Board>();
  boardsSource: Subject<Board[]> = new Subject<Board[]>();

  constructor(private httpClient: HttpClient, private settingsService: SettingsService) {
  }

  getBoards(): Observable<Board[]> {
    return this.httpClient.get<Board[]>(this.settingsService.boardsUrl);
  }

  loadBoards(): void {
    const sub = this.httpClient.get<Board[]>(this.settingsService.boardsUrl)
      .subscribe(value => {
        this.boardsSource.next(value);
        sub.unsubscribe();
      });
  }


  loadBoardById(id: String) {
    const sub = this.httpClient.get<Board>(
      `${this.settingsService.boardsUrl}\\${id}`)
      .subscribe(board => {
        this.currentBoard.next(board);
        sub.unsubscribe();
      });

  }

  createBoard(board: Board) {
    return this.httpClient.post<Board>(
      `${this.settingsService.boardsUrl}`, board)
      .take(1).toPromise();
  }

  updateBoard(board: Board) {
    return this.httpClient.post<Board>(
      `${this.settingsService.boardsUrl}/${board.id}`, board)
      .take(1).toPromise();
  }


  deleteBoard(board: Board) {
    return this.httpClient.delete(
      `${this.settingsService.boardsUrl}/${board.id}`)
      .take(1).toPromise();

  }
}
