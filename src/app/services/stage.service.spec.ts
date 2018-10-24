import {inject, TestBed} from '@angular/core/testing';

import {StageService} from './stage.service';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {SettingsService} from './settings.service';

describe('StageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StageService, HttpClient, HttpHandler, SettingsService]
    });
  });

  it('should be created', inject([StageService], (service: StageService) => {
    expect(service).toBeTruthy();
  }));
});
