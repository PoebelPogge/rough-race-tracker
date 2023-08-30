import { Component } from '@angular/core';
import { TournamentService } from '../tournament/tournament.service';
import { Observable } from 'rxjs';
import { Tournament } from '../tournament/tournament.model';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-racer',
  templateUrl: './racer.component.html',
  styleUrls: ['./racer.component.scss']
})
export class RacerComponent {

  public $tournament: Observable<Tournament>

  constructor(private tournamentService: TournamentService, public dialog: MatDialog){
    this.$tournament = tournamentService.getCurrentTournament();
  }

  openCreateRacerDialog(){
    const dialogRef = this.dialog.open(CreateRacerDialog);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    })
  }
}


@Component({
  selector: 'app-create-racer',
  templateUrl: './create.racer.html',
})
export class CreateRacerDialog {

  number = "";
  firstname = "";
  lastname = "";

  constructor(private tournamentService: TournamentService){}

  submit() {
    this.tournamentService.addRacer({number: this.number, firstName: this.firstname, lastName: this.lastname})
  }

}