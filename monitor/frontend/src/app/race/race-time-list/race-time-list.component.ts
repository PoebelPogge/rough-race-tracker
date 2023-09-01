import { Component, Input } from '@angular/core';
import { RaceService } from '../race.service';
import { Measurement } from 'src/app/measurement/measurement.model';

@Component({
  selector: 'app-race-time-list',
  templateUrl: './race-time-list.component.html',
  styleUrls: ['./race-time-list.component.scss']
})
export class RaceTimeListComponent {

  
  @Input() tagId?: String;

  public measurements: Measurement[] = [];
  public diffs: number[] = [];
  public start?: Measurement;


  constructor(private raceService: RaceService) {
    raceService.getCurrentRace().subscribe(data => {
      console.log(data)
      this.measurements = data.measurements["" + this.tagId];
      this.start = this.measurements[0];
    })

    raceService.getMeasurements().subscribe(data => {
      console.log("Data: ");
      console.log(data);
      var measurements: Measurement[] = data["" + this.tagId] as Measurement[];
      console.log("Measurements: ");
      console.log(measurements);
      this.measurements = measurements;
      this.measurements.forEach(measurement => {
        var lastMeasurement = this.measurements[this.measurements.length-2]
      
        var newTime = new Date(measurement.time).getTime();
        var oldTime = new Date(lastMeasurement.time).getTime();
        var diff =  newTime - oldTime;
        this.diffs.push(diff)
  
        console.log("Messungen: -> " + this.measurements.length)
        console.log("Diffs: -> " + this.diffs.length)
      })
    })
  }
}
