package ElevatorSystem.service;

import ElevatorSystem.model.Elevator;

import java.util.List;

public interface ElevatorSystem {

    // pick up elevator (add request)
    void pickup(int userCurrentFloor, int destinationFloor);

    // simulate one cycle of elevator system execution single floor difference
    void step(List<Elevator> elevators);

    // print status of elevator
    void status(Elevator elevators);
}


