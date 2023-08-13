import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { TournamentService } from './tournament.service';
import { Tournament } from './tournament.model';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.scss']
})
export class TournamentComponent {

  public $tournament: Observable<Tournament>;

  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private tournamentService: TournamentService, public dialog: MatDialog, private router: Router){
    this.$tournament = tournamentService.getCurrentTournament();
  }

  openCreateTournamentDialog() {
    const dialogRef = this.dialog.open(CreateTournamentDialog);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      this.router.navigate(['/race']);
    })
  }
}

@Component({
  selector: 'create-tournament-dialog',
  templateUrl: 'create.tournament.html',
})
export class CreateTournamentDialog {

  constructor(private tournamentService: TournamentService){

  }

  public createTournament() {
    this.tournamentService.createNewTournament("Rethorn");
  }
}
