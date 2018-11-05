import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SettingsService} from './settings.service';
import {Router} from '@angular/router';

@Injectable()
export class LoginService {
  userInfo: any = {};

  constructor(private http: HttpClient,
              private settingsService: SettingsService,
              private router: Router) {
  }

  get authenticated(): boolean {
    return this.userInfo !== {} && this.userInfo.username && this.userInfo.username.length > 0;
  }

  public login(credentials, successCallback, failCallback): void {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get(this.settingsService.backendUrl + '/user', {headers: headers})
      .take(1).toPromise().then((response) => {
        if (response['principal']['username']) {
          this.userInfo = response['principal'];
        } else {
          this.userInfo = {};
        }

        if (successCallback) {
          successCallback();
        }
      },
      reason => failCallback && failCallback(reason)
  );
  }

  public logout() {

    this.http.post('/api/logout', {}).take(1).toPromise().then(() => {
      this.userInfo = {};
      this.router.navigateByUrl('/');
    }, reason => alert('Error while logout ' + JSON.stringify(reason)));

  }

  signup(credentials) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    return this.http.post(this.settingsService.backendUrl + '/user', credentials)
      .take(1).toPromise();
  }
}
