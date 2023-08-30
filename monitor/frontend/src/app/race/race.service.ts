import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Race } from './race.model';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';
import { Measurement } from '../measurement/measurement.model';

@Injectable({
  providedIn: 'root'
})
export class RaceService {

  private BASE_URL: string = "http://localhost:8080/race"
  private socket: WebSocketSubject<any> = webSocket('ws://localhost:8080/race/local-desktop');

  constructor(private http: HttpClient) { }

  public getCurrentRace(): Observable<Race>{
    return this.http.get<Race>(this.BASE_URL);
  }

  public getMeasurements(): Observable<Measurement> {
    return this.socket.asObservable();
  }

  public createNewRace() {
    
  }
}
