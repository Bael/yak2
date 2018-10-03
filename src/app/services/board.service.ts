import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SettingsService} from './settings.service';
import {Observable} from 'rxjs/Observable';
import {Board} from '../board/board';

@Injectable()
export class BoardService {

  constructor(private httpClient: HttpClient, private settingsService: SettingsService) {
  }

  getBoards(): Observable<Board[]> {
    return this.httpClient.get<Board[]>(this.settingsService.boardsUrl);
  }

}
