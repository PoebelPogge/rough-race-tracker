import { Component } from '@angular/core';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'rough-monitor';
  //socket: WebSocketSubject<any> = webSocket('ws://localhost:8080/race/desktop');

  constructor(){

  }

}
