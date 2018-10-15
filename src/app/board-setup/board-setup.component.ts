import {Component, OnInit} from '@angular/core';
import {Board} from '../board/board';
import {BoardService} from '../services/board.service';
import {StageService} from '../services/stage.service';
import {Stage} from '../board/stage';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-board-setup',
  templateUrl: './board-setup.component.html',
  styleUrls: ['./board-setup.component.css']
})
export class BoardSetupComponent implements OnInit {

  board: Board;
  newStageName: String;


  constructor(private boardService: BoardService, private stageService: StageService,
              private activatedRoute: ActivatedRoute, private route: Router) {
  }

  ngOnInit() {
    this.boardService.currentBoard.subscribe(board => {
      this.board = board;
      this.loadStages(this.board);
    });

    const sub = this.activatedRoute.paramMap.subscribe(
      params => {
        // (+) before `params.get()` turns the string into a number
        this.loadBoard(+params.get('id'));
      }
    );

    // this.loadBoard(this.activatedRoute.params['id']);
  }

  loadBoard(boardId: number) {
    this.boardService.loadBoardById(boardId);
  }

  loadStages(board: Board) {
    const s = this.stageService.getStages(board.id).subscribe(value => {
      this.board.stages = value;
      s.unsubscribe();
    });
  }

  onSubmitNewStage(newStageName) {
    const s: Stage = new Stage(newStageName, this.board.id);

    this.stageService.createStage(s).then(() => this.loadStages(this.board));


  }

  deleteStage(stage: Stage) {
    this.stageService.deleteStage(stage).then(() => this.loadStages(this.board),
      reason => alert(JSON.stringify(reason)));
  }

  editStage(stage: Stage, name: String) {
    stage.name = name;
    this.stageService.updateStage(stage).then(value => () => this.loadStages(this.board));

  }

  onDeleteBoard() {
    this.boardService.deleteBoard(this.board).then(value => {
      return this.route.navigate(['boards']);
    }, reason => {
      alert(JSON.stringify(reason));
    });
  }
}
