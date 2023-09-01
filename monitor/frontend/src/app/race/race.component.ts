import { Component } from '@angular/core';
import { TournamentService } from '../tournament/tournament.service';
import { Observable } from 'rxjs';
import { Tournament } from '../tournament/tournament.model';
import { Race } from './race.model';
import { RaceService } from './race.service';
import { MatDialog } from '@angular/material/dialog';
import { Racer } from '../racer/racer.model';

@Component({
  selector: 'app-race',
  templateUrl: './race.component.html',
  styleUrls: ['./race.component.scss']
})
export class RaceComponent {

  public $race: Observable<Race>

  constructor(private raceSercice: RaceService, public dialog: MatDialog){
    this.$race = raceSercice.getCurrentRace();
    raceSercice.getCurrentRace().subscribe(console.log);
  }

  openCreateRacerDialog(){
    const dialogRef = this.dialog.open(AddRacerDialog);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    })
  }

  addRacer(){

  }
}

@Component({
  selector: 'app-add-racer',
  templateUrl: './add.racer.dialog.html',
})
export class AddRacerDialog {

  $tournament: Observable<Tournament>

  constructor(private tournamentService: TournamentService){
    this.$tournament = tournamentService.getCurrentTournament();
  }

  addRacerToRace(racer: Racer){
    
  }
}

