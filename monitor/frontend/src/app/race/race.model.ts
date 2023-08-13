import { Buggy } from "../buggy/buggy.model";
import { Measurement } from "../measurement/measurement.model";

export interface Race {
    id: String;
    creationDate: String;
    buggies: Buggy[];
    state: String;
    measurements: {[key: string]: Measurement[]}
}