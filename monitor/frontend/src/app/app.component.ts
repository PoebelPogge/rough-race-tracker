import { Component } from '@angular/core';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';
import { SystemStatusService } from './system-status/system-status.service';
import { SystemStatus } from './system-status/system-status.model';
import { MatDialog } from '@angular/material/dialog';
import { DeviceNotConnectedDialog } from './system-status/system-status.component';
import { Dialog } from '@angular/cdk/dialog';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'rough-monitor';

  systemStatus?: SystemStatus;

  private dialogOpen = false;

  constructor(private systemStatusService: SystemStatusService, public dialog: MatDialog){
    systemStatusService.getSystemStatus().subscribe(status => {
      if(!status.deviceConnected){
        if(!this.dialogOpen){
          this.dialog.open(DeviceNotConnectedDialog, { disableClose: true })
          this.dialogOpen = true;
        }
        
      } else {
        this.dialog.closeAll();
        this.dialogOpen = false;
      }
      this.systemStatus = status;
    })
  }

}
