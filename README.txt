README, projekt systemu wind w budynku [PL]

Strukture programu podzieliłem na dwie paczki: model oraz service. Program będzie wykonywał się w metodzie Main.

Model - znajdują się tam dwie klasy: Elevator oraz Request. Zawierają pola charakteryzujące klasy Elevator oraz Request 
wraz z getterami/setterami. Do wygenerowania getterów/setterów oraz do toStringa użyłem Lomboka.

Oprócz tego w paczce model mamy enumerator RequestStatus, który zawiera status w jakim znajduje się request.

W paczce service mamy interfejs ElevatorSystem oraz klasę, która implementuje interfejs ElevatorSystem.

Interfejs ElevatorSystem zawiera kontrakt trzech metod:

- pickup -> stworzenie nowego requesta
- step -> symuluje jeden ruch windy (jeden cykl windy)
- status -> informujemy o statusie windy

W klasie ElevatorSystemImpl implemetujemy metody z interfejsu ElevatorSystem.

W metodzie step znajdue się instrukcja warunkowa if else. Część elsowa odpowiada za znalezienie najlepszego requesta do
obslużenia (dzieje się to przy pomocy kolejki priorytetowej). Następnie dobierana jest do tego requesta najlepsza winda (kolejka
priorytetowa). Potem w metodzie prywatnej updateElevatorStatus aktualizujemy dane windy (tylko jeden krok). Po tym metoda step się kończy.

Część ifowa odpowiada za kontynuacje obsługi wybranego wcześniej requesta (ale pamietamy, ze to tylko jeden krok).

W metodzie Main pomijając inicjalizacje elevatorów oraz dodanie requestow program wykonuje się iterując po requestach (foreach).
Potem jak już się doczepi "konkretnego" requesta to będzie na nim wykonywał metodę step, dopóki status tego requesta nie zmieni się
na COMPLETED.

Program niestety nie do końca dobrze działa. Nie ma obsługi wielowątkowości, najlepsza winda dobrana do najlepszego requesta 
"teleportuje" się do niego. Do windy mieści się tylko jedna osoba. 

Do programu można by dodać jeszcze testy jednostkowe, które by testowały metody z serwisu. Najbardziej interesującą metodą do przetestowania 
byłaby metoda step. Poszczególne jednostkowe testy nie testowałyby jej jako całość tylko skupiłyby sie na jej fragmentach (np.: osobno if,
osobno else). Ta metoda bardzo dużo korzysta z metod prywatnych. Można by mockować ich zachowanie w testach.

Mankamentem, który mógłby nam przeszkodzić w wykonaniu testów jest statyczna lista requestów. Testowanie statycznych instacji nie jest wygodne.


###############################################################################################################################################
###############################################################################################################################################
###############################################################################################################################################


README, design of a lift system in a building [EN]

I have divided the structure of the program into two packages: model and service. The program will be executed in the Main method.

Model - there are two classes: Elevator and Request. They contain the fields that characterise the Elevator and Request classes 
together with getters/setters. I used Lombok to generate the getters/setters and toString method.

In addition, in the model package we have the RequestStatus enumerator, which contains the status the request is in.

In the service package we have the ElevatorSystem interface and a class that implements the ElevatorSystem interface.

The ElevatorSystem interface contains a contract of three methods:

- pickup -> create a new request
- step -> simulates one lift movement (one lift cycle)
- status -> informs about the status of the lift

In the ElevatorSystemImpl class, we implement methods from the ElevatorSystem interface.

The step method contains the conditional instruction if else. The else part is responsible for finding the best request to
to handle (this is done using a priority queue). Then the best lift is selected for this request (priority queue). Then in the private method updateElevatorStatus we update the lift data (only one step). 
After that, the step method ends.

The if part is responsible for the continuation of the handling of the previously selected request (but we remember that it is only one step).

In the Main method, apart from initialising the elevators and adding the requests, the program iterates through the requests (foreach).
Then, once a "specific" request is added, it will execute the step method on it until the status of the request changes to COMPLETED.

Unfortunately, the program does not quite work well. There is no multithreading support, the best lift matched to the best request 
"teleports" to it. Only one person fits into the lift. 

Unit tests could still be added to the programme to test methods from the service. The most interesting method to test 
would be the step method. The individual unit tests would not test it as a whole, but would focus on parts of it (e.g.: separately if,
separately else). This method uses private methods a lot. You could mock their behaviour in the tests.

A drawback that could prevent us from testing is the static list of requests. Testing static instances is not convenient.