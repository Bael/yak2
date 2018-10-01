import {Injectable} from '@angular/core';

@Injectable()
export class SettingsService {
  constructor() {
  }

  private _backendUrl = 'http://localhost:8080/api';

  get backendUrl(): string {
    return this._backendUrl;
  }

  get tasksUrl(): string {
    return this.backendUrl + '/tasks';
  }

  get stagesUrl(): string {
    return this.backendUrl + '/stages';
  }

  get boardsUrl(): string {
    return this.backendUrl + '/boards';
  }

}
