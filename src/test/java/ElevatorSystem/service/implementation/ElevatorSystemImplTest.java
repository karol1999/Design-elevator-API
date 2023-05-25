package ElevatorSystem.service.implementation;

import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import ElevatorSystem.model.RequestStatus;
import ElevatorSystem.service.ElevatorSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ElevatorSystemImplTest {

    final ElevatorSystem elevatorSystem = new ElevatorSystemImpl();
    List<Request> requests;

    @BeforeEach
    void setUp() {
        requests = List.of(
                new Request(3, 7),
                new Request(2, 9));
    }

    @Test
    void checkElevatorInMove() {


        List<Elevator> elevators = List.of(
                new Elevator(1, 4),
                new Elevator(2, 5));

        Request request = requests.get(0);
        request.setRequestStatus(RequestStatus.IN_PROGRESS);
        request.setElevatorID(1);

        elevatorSystem.step(elevators);
        // assertEquals(5, elevators.get(0).getCurrentFloor());

    }

}