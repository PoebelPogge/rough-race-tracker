import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'laptime'
})
export class LaptimePipe implements PipeTransform {

  transform(value: number[], ...args: unknown[]): number {
    var result = 0;
    value.forEach(v => result += v);
    return result / 1000;
  }
}
