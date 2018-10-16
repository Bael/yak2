import {Component, OnInit} from '@angular/core';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Task} from '../task';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {

  task: Task;

  constructor(private taskService: TaskService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }


  onSubmit() {

    this.taskService.updateTask(this.task).take(1).toPromise().then(() => this.router.navigateByUrl('boards'));

  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {

      this.taskService.findTask(parseInt(value.get('id'), 10)).subscribe(taskValue => {
        console.log('get task ' + taskValue);
        this.task = taskValue;
      });
    });
  }

}
