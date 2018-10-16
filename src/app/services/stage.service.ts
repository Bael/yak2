import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SettingsService} from './settings.service';
import {Stage} from '../board/stage';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/take';

@Injectable()
export class StageService {

  constructor(private httpClient: HttpClient, private settingsService: SettingsService) {
  }

  getStages(id: number): Observable<Stage[]> {
    return this.httpClient.get<Stage[]>(`${this.settingsService.boardsUrl}/${id}/stages`);
  }

  createStage(stage: Stage) {

    return this.httpClient.post<Stage>(`${this.settingsService.stagesUrl}`, stage).take(1).toPromise();
  }

  deleteStage(stage: Stage) {
    return this.httpClient.delete(`${this.settingsService.stagesUrl}/${stage.id}`).take(1).toPromise();
  }

  updateStage(stage: Stage) {
    return this.httpClient.put<Stage>(`${this.settingsService.stagesUrl}/${stage.id}`, stage).take(1).toPromise();
  }
}
