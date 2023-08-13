import { Component } from '@angular/core';
import { TournamentService } from '../tournament/tournament.service';
import { Observable } from 'rxjs';
import { Tournament } from '../tournament/tournament.model';

@Component({
  selector: 'app-racer',
  templateUrl: './racer.component.html',
  styleUrls: ['./racer.component.scss']
})
export class RacerComponent {

  public $tournament: Observable<Tournament>

  constructor(private tournamentService: TournamentService){
    this.$tournament = tournamentService.getCurrentTournament();
  }
}
