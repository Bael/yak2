import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Task} from '../board/task';

@Injectable()
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTasks(): Observable<Object> {


    return this.httpClient.get('http://localhost:8080/tasks');
    /*
    return new Promise((resolve, reject) => {
      this.http.get(`/visits?location=${location}`)
        .subscribe(res => {
          resolve(res as Place[]);
        }, (err) => {
          console.log("err occured: " + err);
          reject(err);
        });
    });*/
  }


  createTask(task: Task): Observable<Object> {
    return this.httpClient.post('http://localhost:8080/tasks', task);
  }



}
