import {inject, TestBed} from '@angular/core/testing';

import {StageService} from './stage.service';

describe('StageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StageService]
    });
  });

  it('should be created', inject([StageService], (service: StageService) => {
    expect(service).toBeTruthy();
  }));
});
