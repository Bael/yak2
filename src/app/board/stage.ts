import {Task} from 'app/board/task';

export class Stage {
  id: number;
  name: String;
  boardId: number;
  tasks: Task[];


  constructor(name: String, boardId: number) {
    this.name = name;
    this.boardId = boardId;
  }
}


