import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CreateTournamentDialog, TournamentComponent } from './tournament/tournament.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import { HttpClientModule } from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { RaceComponent } from './race/race.component';
import { RacerComponent } from './racer/racer.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTableModule} from '@angular/material/table';
import { RaceTimeListComponent } from './race/race-time-list/race-time-list.component';
import { LaptimePipe } from './race/laptime.pipe';

@NgModule({
  declarations: [
    AppComponent,
    TournamentComponent,
    CreateTournamentDialog,
    RaceComponent,
    RacerComponent,
    RaceTimeListComponent,
    LaptimePipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatTabsModule,
    HttpClientModule,
    MatDialogModule,
    MatCardModule,
    MatGridListModule,
    MatTableModule,
    RouterModule.forRoot([
      { path: '', redirectTo: 'race', pathMatch: 'full'},
      { path: 'race', component: RaceComponent},
      { path: 'tournament', component: RaceComponent},
      { path: 'racer', component: RacerComponent}
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
