import {Task} from 'app/board/task';

export class Stage {
  id: String;
  name: String;
  boardId: String;
  tasks: Task[];


  constructor(name: String, boardId: String) {
    this.name = name;
    this.boardId = boardId;
  }
}


