package ElevatorSystem.service.implementation;

import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import ElevatorSystem.model.RequestStatus;
import ElevatorSystem.service.ElevatorSystem;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ElevatorSystemImpl implements ElevatorSystem {


    private static List<Request> requests = new ArrayList<>();

    @Override
    public void pickup(int userCurrentFloor, int destinationFloor) {
        requests.add(new Request(userCurrentFloor, destinationFloor));
    }

    @Override
    public void step(List<Elevator> elevators) {

        if (isElevatorInMove()) {

            Optional<Elevator> optionalElevator = getElevator(elevators);
            Elevator bestElevatorInMove;

            if (optionalElevator.isPresent()) {
                bestElevatorInMove = optionalElevator.get();

                updateElevatorStatus(bestElevatorInMove);
                status(bestElevatorInMove);
            }

        } else {

            Optional<Request> optionalTopPriorityRequest = computeTopPriorityRequest();
            Request topPriorityRequest;

            if (optionalTopPriorityRequest.isPresent()) {
                topPriorityRequest = optionalTopPriorityRequest.get();

                Optional<Elevator> optionalBestElevatorAssignedToTopPriorityRequest = computeBestElevator(elevators, topPriorityRequest);
                Elevator bestElevatorAssignedToTopPriorityRequest;

                if (optionalBestElevatorAssignedToTopPriorityRequest.isPresent()) {
                    bestElevatorAssignedToTopPriorityRequest = optionalBestElevatorAssignedToTopPriorityRequest.get();
                    bestElevatorAssignedToTopPriorityRequest.setCurrentFloor(topPriorityRequest.getCurrentFloor());

                    updateTopPriorityRequest(topPriorityRequest, bestElevatorAssignedToTopPriorityRequest);
                    status(bestElevatorAssignedToTopPriorityRequest);
                    updateElevatorStatus(bestElevatorAssignedToTopPriorityRequest);
                    status(bestElevatorAssignedToTopPriorityRequest);
                }

            }

        }

    }

    private static void updateTopPriorityRequest(Request topPriorityRequest, Elevator bestElevatorAssignedToTopPriorityRequest) {
        topPriorityRequest.setElevatorID(bestElevatorAssignedToTopPriorityRequest.getId());
        topPriorityRequest.setRequestStatus(RequestStatus.IN_PROGRESS);
    }

    private Optional<Elevator> getElevator(List<Elevator> elevators) {
        return findBestElevatorInMove(elevators);
    }

    private Optional<Elevator> findBestElevatorInMove(List<Elevator> elevators) {
        for (Request request : requests) {
            if (request.getRequestStatus().equals(RequestStatus.PENDING) || request.getRequestStatus().equals(RequestStatus.COMPLETED))
                continue;
            for (Elevator elevator : elevators) {
                if (elevator.getId() == request.getElevatorID()) return Optional.of(elevator);
            }
        }

        return Optional.empty();
    }

    private boolean isElevatorInMove() {
        for (Request request : requests) {
            if (request.getRequestStatus().equals(RequestStatus.IN_PROGRESS)) {
                return true;
            }
        }

        return false;
    }

    private Optional<Request> computeTopPriorityRequest() {
        Map<Request, Integer> requestToRequestPriority = new HashMap<>();
        Queue<Integer> requestPriority = new PriorityQueue<>();

        for (Request request : requests) {
            if (request.getRequestStatus().equals(RequestStatus.COMPLETED)) continue;
            requestPriority.add(Math.abs(request.getDestinationFloor() - request.getCurrentFloor()));
            requestToRequestPriority.put(request, Math.abs(request.getDestinationFloor() - request.getCurrentFloor()));
        }

        for (Map.Entry<Request, Integer> entry : requestToRequestPriority.entrySet()) {
            if (Objects.equals(entry.getValue(), requestPriority.peek())) {
                return Optional.of(entry.getKey());
            }
        }

        return Optional.empty();
    }

    private Optional<Elevator> computeBestElevator(List<Elevator> elevators, Request topPriorityRequest) {
        Map<Elevator, Integer> elevatorToBestPathRequest = new HashMap<>();
        Queue<Integer> elevatorPriority = new PriorityQueue<>();

        for (Elevator elevator : elevators) {
            elevatorPriority.add(Math.abs(elevator.getCurrentFloor() - topPriorityRequest.getCurrentFloor()));
            elevatorToBestPathRequest.put(elevator, Math.abs(elevator.getCurrentFloor() - topPriorityRequest.getCurrentFloor()));
        }

        for (Map.Entry<Elevator, Integer> entry : elevatorToBestPathRequest.entrySet()) {
            if (Objects.equals(entry.getValue(), elevatorPriority.peek())) {
                entry.getKey().setFloorsToGo(topPriorityRequest.getCurrentFloor() - topPriorityRequest.getDestinationFloor());
                return Optional.of(entry.getKey());
            }
        }

        return Optional.empty();
    }

    private void updateElevatorStatus(Elevator elevator) {

        if (elevator.getFloorsToGo() != 0) {
            if (elevator.getFloorsToGo() > 0) { // going down
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                elevator.setFloorsToGo(elevator.getFloorsToGo() - 1);
            } else { // going up
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                elevator.setFloorsToGo(elevator.getFloorsToGo() + 1);
            }
        }

        if (elevator.getFloorsToGo() == 0) {
            for (Request request : requests) {
                if (request.getElevatorID() == elevator.getId()) {
                    request.setRequestStatus(RequestStatus.COMPLETED);
                    break;
                }
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public void status(Elevator elevator) {

        log.info("Elevator ID: {}, floors to go: {}, current floor: {}",
                elevator.getId(),
                elevator.getFloorsToGo(),
                elevator.getCurrentFloor());

    }

    public static List<Request> getRequests() {
        return requests;
    }

}
