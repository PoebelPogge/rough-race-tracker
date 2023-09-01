import { Injectable } from '@angular/core';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { SystemStatus } from './system-status.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SystemStatusService {

  private socket: WebSocketSubject<any> = webSocket('ws://localhost:8080/status/local-desktop');


  constructor() { }

  public getSystemStatus(): Observable<SystemStatus> {
    return this.socket.asObservable();
  }
}
