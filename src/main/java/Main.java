import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import ElevatorSystem.model.RequestStatus;
import ElevatorSystem.service.ElevatorSystem;
import ElevatorSystem.service.implementation.ElevatorSystemImpl;

import java.util.*;

public class Main {

    // program constraints:
    // -> we do have 11 floors - from 0 to 10
    // -> we can have at most 16 elevators
    // -> we can have at most 16 people
    // -> one person -> one request
    // -> one person fits to only one elevator

    private final static int MAX_FLOOR = 10;
    private final static int MIN_FLOOR = 0;

    public static void main(String[] args) {

        System.out.println("Elevator system simulation");

        List<Elevator> elevators = new ArrayList<>();
        int numberOfElevators = getNumberOfElevators();

        setElevatorsToFloors(elevators, numberOfElevators);

        ElevatorSystem elevatorSystem = new ElevatorSystemImpl();

        Scanner input = new Scanner(System.in);

        // enter requests
        enterRequests(elevatorSystem, input);

        System.out.println();

        // iteracja jezeli sa itemy w liscie requestow
        for(Request request : ElevatorSystemImpl.getRequests()) {
            while(request.getRequestStatus() != RequestStatus.COMPLETED) {
                elevatorSystem.step(elevators);
            }
            System.out.println("Request is made");
        }

        System.out.println("End");


        // while true
        // 1 - add requests
        // 2 - step
        // 3 status
    }

    private static void setElevatorsToFloors(List<Elevator> elevators, int numberOfElevators) {
        Random rng = new Random();

        for (int id = 0; id < numberOfElevators; id++) {
            elevators.add(new Elevator(id, rng.nextInt((MAX_FLOOR - MIN_FLOOR) + 1) + MIN_FLOOR));
        }
    }

    private static void enterRequests(ElevatorSystem elevatorSystem, Scanner input) {
        int endFloor = 0;
        int startFloor = 0;
        boolean isContinued = true;

        while (isContinued) {

            System.out.println("Add request");
            System.out.print("Starting point, can be between 0 and 10: ");
            startFloor = input.nextInt();

            System.out.print("End point, can be between 0 and 10: ");
            endFloor = input.nextInt();

            elevatorSystem.pickup(startFloor, endFloor);

            System.out.print("Do you want to enter more requests? Enter 1 for YES and 0 for NO: ");
            if (input.nextInt() == 0) {
                isContinued = false;
            }

        }
    }

    private static int getNumberOfElevators() {
        Scanner sc = new Scanner(System.in);
        int numberOfElevators;
        do {
            System.out.println("Please enter a positive number of elevators. You can have at most 16.");
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number.");
                sc.next();
            }
            numberOfElevators = sc.nextInt();
        } while (numberOfElevators <= 0 || numberOfElevators > 16);
        System.out.println("Thank you! Got " + numberOfElevators);
        return numberOfElevators;
    }
}

