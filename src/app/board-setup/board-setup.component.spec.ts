import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BoardSetupComponent} from './board-setup.component';

describe('BoardSetupComponent', () => {
  let component: BoardSetupComponent;
  let fixture: ComponentFixture<BoardSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BoardSetupComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
