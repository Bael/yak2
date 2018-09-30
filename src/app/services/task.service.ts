import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Task} from '../board/task';
import {SettingsService} from './settings.service';

@Injectable()
export class TaskService {

  constructor(private httpClient: HttpClient, private settingsService: SettingsService) {
  }

  getTasks(): Observable<Task[]> {
    return this.httpClient.get<Task[]>(this.settingsService.tasksUrl);
  }

  getTasksByStageId(stageId: Number): Observable<Task[]> {
    return this.httpClient.get<Task[]>(`${this.settingsService.backendUrl}/stages/${stageId}/tasks`);
  }

  createTask(task: Task): Observable<Task> {
    return this.httpClient.post<Task>(this.settingsService.tasksUrl, task);
  }

  updateTask(task: Task): Observable<Task> {
    return this.httpClient.put<Task>(`${this.settingsService.tasksUrl}/${task.id}`, task);
  }

  deleteTask(task: Task): Observable<Task> {
    return this.httpClient.delete<Task>(`${this.settingsService.tasksUrl}/${task.id}`);
  }


}
