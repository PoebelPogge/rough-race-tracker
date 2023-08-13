import { Race } from "../race/race.model";
import { Racer } from "../racer/racer.model";

export interface Tournament {
    timestamp:String;
    city:String;
    participants:Racer[];
    races:Race[];
}