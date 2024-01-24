package presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.*;
import model.map.WorldMap;
import model.observers.MapChangeListener;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private final int CELL_SIZE = 40;
    private WorldMap map;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label test;

    private double maxAnimalEnergy;
    private double averageAnimalEnergy;
    private boolean pause = false;

    //Do statystyk
    @FXML
    Label animalNumber;
    @FXML
    Label grassNumber;
    @FXML
    Label freePlacesNumber;
    @FXML
    Label averageEnergy;
    @FXML
    Label averageNumberOfKids;
    @FXML
    Label averageLengthOfLifeOfDeadAnimal;
    //private int

    public void setMap(WorldMap map) {
        this.map = map;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.println("TUUUUUUUUUUUUUUUUUUUUUUUUUUUUOOOOOOOOOOOOOOOOOO");
        Platform.runLater(() -> drawMap(message));
    }

    public void drawMap(String message) {
        clearGrid();
        updateGrid();
        test.setText("Tu się będzie wszystko działo");
        updateStats();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void updateGrid() {
        calculateMaxAndAverageAnimalEnergy();
        Label label00 = new Label();
        mapGrid.add(label00, 0, 0);
        label00.setText("y\\x");
        GridPane.setHalignment(label00, HPos.CENTER);

        for (int i = 0; i < map.getWidth() + 1; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i < map.getHeight() + 1; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        //Rows' names
        for (int i = 1; i <= map.getWidth(); i++) {
            Label label = new Label();
            mapGrid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            label.setText(String.valueOf(i-1));
        }

        //Columns' names
        for (int i = 1; i <= map.getHeight(); i++) {
            Label label = new Label();
            mapGrid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
            label.setText(String.valueOf(i - 1));
        }

        if (pause) {
            for (int i = map.getEquatorBottomLane(); i <= map.getEquatorUpLane(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    Rectangle onEquatorPosition = new Rectangle(CELL_SIZE, CELL_SIZE, Color.rgb(0, 255, 0));
                    GridPane.setHalignment(onEquatorPosition, HPos.CENTER);
                    mapGrid.add(onEquatorPosition, j+1, i+1);
                }
            }
        }

        HashMap<Vector2d, List<WorldElement>> elements = map.getElements();
        for (Vector2d position: elements.keySet()) {
            Node objectPlacedOnTheGrid = null;
            Label label = new Label();
            mapGrid.add(label, position.getX() + 1, position.getY() + 1);


            for (int i = elements.get(position).size() - 1; i >= 0; i--) {
                if (elements.get(position).get(i) instanceof Animal)
                    objectPlacedOnTheGrid = createAnimal((Animal) elements.get(position).get(i), position);
                else if (elements.get(position).get(i) instanceof Plant)
                    objectPlacedOnTheGrid = createPlant();
                else if (elements.get(position).get(i) instanceof Tunnel)
                    objectPlacedOnTheGrid = createTunnel();
            }
            GridPane.setHalignment(objectPlacedOnTheGrid, HPos.CENTER);
            mapGrid.add(objectPlacedOnTheGrid, position.getX() + 1, position.getY() + 1);
            //label.setText(elements.get(position).get(i).toString());
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private Node createAnimal(Animal animal, Vector2d position) {
        double animalEnergyInComparison = animal.getEnergy() / maxAnimalEnergy;
        Color animalColor = Color.rgb((int) animalEnergyInComparison * 255, 0, 255);

        Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE, Color.TRANSPARENT);
        Node drawAnimal = new Rectangle(CELL_SIZE * 0.8, CELL_SIZE * 0.8, animalColor);
        background.setMouseTransparent(false);
        drawAnimal.setMouseTransparent(true);
        GridPane.setHalignment(background, HPos.CENTER);
        mapGrid.add(background, position.getX() + 1, position.getY() + 1);

        //Po kliknięciu na background mają się wyświetlać informacje
        //background.setOnMouseClicked(event -> animalInfoDislpay(animal));
        return drawAnimal;
    }

    private Node createPlant() {
        Rectangle drawPlant = new Rectangle(CELL_SIZE * 0.6, CELL_SIZE * 0.6, Color.rgb(0, 204, 0));
        return drawPlant;
    }

    private Node createTunnel() {
        Circle drawTunnel = new Circle((double) CELL_SIZE * 0.5, Color.rgb(102, 51, 0));
        return drawTunnel;
    }

    private void calculateMaxAndAverageAnimalEnergy() {
        double sum = 0;
        HashMap<Vector2d, List<Animal>> animals = map.getAnimals();
        for (Vector2d position: animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                maxAnimalEnergy = Math.max(maxAnimalEnergy, animal.getEnergy());
                sum += animal.getEnergy();
            }
        }
        averageAnimalEnergy = sum / map.getAnimalsAlive();
    }

    private void updateStats() {
        animalNumber.setText("Number of animals: " + map.getAnimalsAlive());
        grassNumber.setText("Number of grass: " + map.getNumOfPlants());
        freePlacesNumber.setText("Free places: " + map.getNumOfFreePositions());
        averageEnergy.setText("Average animal energy: " + averageAnimalEnergy);
        //averageNumberOfKids.setText("Average number of kids: "+ );
        averageLengthOfLifeOfDeadAnimal.setText("Average length of life\nof dead animal: " + map.getAverageLifeLengthOfDeadAnimals());

    }
}