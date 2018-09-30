import {Task} from 'app/board/task';

export class Stage {
  id: number;
  name: String;
  boardId: number;
  tasks: Task[];


  constructor(id: number, name: String, boardId: number) {
    this.id = id;
    this.name = name;
    this.boardId = boardId;
  }
}


