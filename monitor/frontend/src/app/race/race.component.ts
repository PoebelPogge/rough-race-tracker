import { Component } from '@angular/core';
import { TournamentService } from '../tournament/tournament.service';
import { Observable } from 'rxjs';
import { Tournament } from '../tournament/tournament.model';
import { Race } from './race.model';
import { RaceService } from './race.service';

@Component({
  selector: 'app-race',
  templateUrl: './race.component.html',
  styleUrls: ['./race.component.scss']
})
export class RaceComponent {

  public $race: Observable<Race>

  constructor(private raceSercice: RaceService){
    this.$race = raceSercice.getCurrentRace();
  }
}
