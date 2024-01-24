# Darwin Evolution
A simulation of animals' lives around the world with different configurations.

### Krótki opis

Po uruchomieniu programu możemy skonfigurować symulację, wybrać dodatkowe warianty - tunele i skaczący genom - oraz zapisać wyniki w pliku CSV.
Spacja służy do wyświetlenia równika. Możemy obserwować zwierzę - domyślnie obserwowane jest zwierzę nr 1.
***
### Podział pracy
**Tomasz Paja:**
* klasy dotyczące map, elementów mapy (zwierząt, roślin i tuneli), genomów
* interfejsy i klasy abstrakcyjne dotyczące powyższych klas
* typy wyliczeniowe dla map, genomów i kierunków poruszania zwierząt
* klasy dla podstawowych operacji: Vector2d, MapDirection
* komparator zwierząt i generator niepowtarzających się losowych wartości z danej tablicy
* klasa Simulation
* testy

**Szymon Paja:**
* panel konfiguracyjny
* okno symulacji
* cały FXML
* GUI
* obserwacja zwierzaka
* zapisywanie do pliku CSV
* statystyki i funkcje je obsługujące w klasie AbstractWorldMap
* prezenterzy dla symulacji
* klasa SimulationEngine
