import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Stage} from '../stage';
import {Task} from '../task';
import {FormControl} from '@angular/forms';
import {BoardService} from '../../services/board.service';
import {Board} from '../board';
import {Router} from '@angular/router';


@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrls: ['./stage.component.css']
})
export class StageComponent implements OnInit, OnDestroy {

  @Input()
  stage: Stage;
  @Input()
  board: Board;

  @Output()
  moveAheadTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();

  @Output()
  moveBackTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();

  newTaskText = '';
  @Input()
  moveBackEnabled: boolean;
  @Input()
  moveAheadEnabled: boolean;
  taskNameFormControl = new FormControl('',);

  constructor(private boardService: BoardService, private router: Router) {
  }


  createTask() {
    const task = new Task();
    task.name = this.newTaskText;
    task.priority = 0;
    task.stageId = this.stage.id;

    this.stage.tasks.push(task);
    this.boardService.updateBoard(this.board).then(
      () => this.router.navigateByUrl(`/board/${this.board.id}`)
    );
    this.newTaskText = '';
    this.taskNameFormControl.reset();
    this.sortTasks();

  }

  sortTasks() {
    this.stage.tasks.sort((a, b) => b.priority - a.priority);
  }

  onChangePriority($event: Task) {
    this.sortTasks();

  }

  filterTasks(taskToHide: Task) {
    this.stage.tasks = this.stage.tasks.filter(task => task !== taskToHide);
  }

  onMoveBackEvent(task: Task) {
    this.filterTasks(task);
    this.moveBackTaskEvent.emit(task);
  }

  onMoveAheadEvent(task: Task) {
    this.filterTasks(task);
    this.moveAheadTaskEvent.emit(task);
  }

  ngOnDestroy(): void {
  }

  onDeleteTaskEvent(taskToDelete: Task) {
    this.stage.tasks = this.stage.tasks.filter(value => value !== taskToDelete);
    this.boardService.updateBoard(this.board);
  }

  ngOnInit(): void {
  }
}
