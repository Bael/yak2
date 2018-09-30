import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SettingsService} from './settings.service';
import {Stage} from '../board/stage';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class StageService {

  constructor(private httpClient: HttpClient, private settingsService: SettingsService) {
  }

  getStages(): Observable<Stage[]> {
    return this.httpClient.get<Stage[]>(this.settingsService.stagesUrl);
  }

}
