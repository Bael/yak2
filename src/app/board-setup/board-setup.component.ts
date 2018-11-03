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
    });

    const sub = this.activatedRoute.paramMap.subscribe(
      params => {
        this.loadBoard(params.get('id').toString());
      }
    );
  }

  loadBoard(boardId: String) {
    this.boardService.loadBoardById(boardId);
  }


  onSubmitNewStage(newStageName) {
    const s: Stage = new Stage(newStageName, this.board.id);
    this.board.stages.push(s);
    this.boardService.updateBoard(this.board).then(value => console.log(value));
  }

  deleteStage(stage: Stage) {
    this.board.stages = this.board.stages.filter(value => value !== stage);
    this.boardService.updateBoard(this.board).then(value => console.log(value));
  }

  editStage(stage: Stage, name: String) {
    stage.name = name;
    this.boardService.updateBoard(this.board).then(value => console.log(value));
  }

  onDeleteBoard() {
    this.boardService.deleteBoard(this.board).then(value => {
      return this.route.navigate(['boards']);
    }, reason => {
      alert(JSON.stringify(reason));
    });
  }
}
