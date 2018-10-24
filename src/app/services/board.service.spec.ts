import {inject, TestBed} from '@angular/core/testing';

import {BoardService} from './board.service';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {SettingsService} from './settings.service';

describe('BoardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BoardService,  HttpClient, HttpHandler, SettingsService]
    });
  });

  it('should be created', inject([BoardService], (service: BoardService) => {
    expect(service).toBeTruthy();
  }));
});
