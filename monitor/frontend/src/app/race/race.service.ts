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
  private measurementsSocket: WebSocketSubject<any> = webSocket('ws://localhost:8080/race/local-desktop/measurements');
  private currentRaceSocket: WebSocketSubject<any> = webSocket('ws://localhost:8080/race/local-desktop');


  constructor() { }

  public getCurrentRace(): Observable<Race>{
    console.log("getCurrentRace called");
    return this.currentRaceSocket.asObservable();
  }

  public getMeasurements(): Observable<any> {
    return this.measurementsSocket.asObservable();
  }

  public createNewRace() {
    
  }
}
