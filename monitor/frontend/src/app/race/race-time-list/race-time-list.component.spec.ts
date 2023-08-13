import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceTimeListComponent } from './race-time-list.component';

describe('RaceTimeListComponent', () => {
  let component: RaceTimeListComponent;
  let fixture: ComponentFixture<RaceTimeListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RaceTimeListComponent]
    });
    fixture = TestBed.createComponent(RaceTimeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
