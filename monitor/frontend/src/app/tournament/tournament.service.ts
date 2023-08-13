import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tournament } from './tournament.model';
import { Racer } from '../racer/racer.model';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  private BASE_URL: string = "http://localhost:8080/tournament"


  constructor(private http: HttpClient) { }

  getCurrentTournament(): Observable<Tournament> {
    return this.http.get<Tournament>(this.BASE_URL);
  }

  createNewTournament(city: string) {
    this.http.post<string>(this.BASE_URL,null, {params: {city: city}}).subscribe();
  }

  addRacer(racer: Racer){
    this.http.post<string>(this.BASE_URL + "/racer", racer).subscribe();
  }
}
