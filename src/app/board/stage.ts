export class Stage {
  name: String;

  constructor(name: String) {
    this.name = name;
  }
}

export const Stages: Stage[] = [new Stage('backlog'), new Stage('plan'), new Stage('work'), new Stage('done')];

