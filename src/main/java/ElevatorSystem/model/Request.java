package ElevatorSystem.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Request {
    private int currentFloor;
    private int destinationFloor;
    private int elevatorID;
    private RequestStatus requestStatus;

    public Request(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        elevatorID = -1;
        requestStatus = RequestStatus.PENDING;
    }

}

