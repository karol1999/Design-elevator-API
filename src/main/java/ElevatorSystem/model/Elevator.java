package ElevatorSystem.model;

import lombok.Data;

@Data
public class Elevator {
    private int floorsToGo; // where do we want to go (up or down and how much)
    private int currentFloor;
    private int id;

    public Elevator(int id, int currentFloor) {
        this.currentFloor = currentFloor;
        this.id = id;
        floorsToGo = 0;
    }

}
