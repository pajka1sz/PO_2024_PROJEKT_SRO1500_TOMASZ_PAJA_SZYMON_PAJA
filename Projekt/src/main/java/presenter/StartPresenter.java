package presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GenomType;
import model.Simulation;
import model.SimulationEngine;
import model.map.MapType;
import model.map.WorldMap;
import model.observers.MapChangeListener;

import java.io.IOException;
import java.util.List;

public class StartPresenter {
    private int ID = 0;
    @FXML
    public ComboBox<Integer> width;
    @FXML
    public ComboBox<Integer> height;
    @FXML
    public Spinner<Integer> animalsAmount;
    @FXML
    public ComboBox<Integer> genLength;
    @FXML
    public Spinner<Integer> grassAmount;
    @FXML
    public ComboBox<Integer> grassEnergy;
    @FXML
    public Spinner<Integer> startEnergy;
    @FXML
    public ComboBox<Integer> grassByDay;
    @FXML
    public ComboBox<Integer> minimalGenMutations;
    @FXML
    public ComboBox<Integer> maximalGenMutations;
    @FXML
    public ComboBox<Integer> energyToReproduce;
    @FXML
    public ComboBox<Integer> energyLostEveryDay;
    @FXML
    public CheckBox tunnel;
    @FXML
    public CheckBox crazyGenom;
    @FXML
    public ComboBox<Integer> tunnelNumber;
    @FXML
    public ComboBox<Integer> reproduceEnergy;

    // Ustawienie TextFormattera dla TextField
    TextFormatter<Integer> textFormatter = new TextFormatter<>(
            c -> {
                if (c.getControlNewText().matches("-?\\d*")) {
                    return c;
                } else {
                    return null;
                }
            }
    );

    @FXML
    private void initialize() {
        for (int i = 1; i <= 50; i++) {
            width.getItems().add(i);
            height.getItems().add(i);
        }
        width.setValue(10);
        height.setValue(10);
        for (int i = 0; i < 15; i++) {
            grassEnergy.getItems().add(i);
            genLength.getItems().add(i);
            energyLostEveryDay.getItems().add(i);
        }
        grassEnergy.setValue(5);
        genLength.setValue(5);
        energyLostEveryDay.setValue(2);
        for (int i = 0; i <= 10; i++) {
            energyToReproduce.getItems().add(i);
        }
        energyToReproduce.setValue(7);
        for (int i = 0; i <= 15; i++) {
            reproduceEnergy.getItems().add(i);
        }
        reproduceEnergy.setValue(5);


        SpinnerValueFactory<Integer> startEnergySpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 5);
        startEnergy.setValueFactory(startEnergySpinner);
        startEnergy.setEditable(true);

        TextField textField = startEnergy.getEditor();
        textField.setTextFormatter(textFormatter);

        setTunnelNumber();
        setAnimalAmount();
        setGrassAmount();
        setGrassByDay();
        setGenomMutations();
    }

    @FXML
    private void setTunnelNumber() {
        tunnelNumber.getItems().clear();
        for (int i = 0; i <= width.getValue()* height.getValue()/2; i++) {
            tunnelNumber.getItems().add(i);
        }
        tunnelNumber.setValue(2);
    }

    @FXML
    private void setAnimalAmount() {
        SpinnerValueFactory<Integer> animals = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, width.getValue()* height.getValue(), 10);
        animalsAmount.setValueFactory(animals);
        animalsAmount.setEditable(true);

        TextFormatter<Integer> textFormatter = new TextFormatter<>(
                c -> {
                    if (c.getControlNewText().matches("-?\\d*")) {
                        return c;
                    } else {
                        return null;
                    }
                }
        );

        TextField textField2 = animalsAmount.getEditor();
        textField2.setTextFormatter(textFormatter);
    }

    @FXML
    private void setGrassAmount() {
        SpinnerValueFactory<Integer> grass = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, width.getValue()* height.getValue(), 10);
        grassAmount.setValueFactory(grass);
        grassAmount.setEditable(true);

        TextFormatter<Integer> textFormatter = new TextFormatter<>(
                c -> {
                    if (c.getControlNewText().matches("-?\\d*")) {
                        return c;
                    } else {
                        return null;
                    }
                }
        );

        TextField textField = grassAmount.getEditor();
        textField.setTextFormatter(textFormatter);
    }

    @FXML
    private void setGrassByDay() {
        grassByDay.getItems().clear();
        for (int i = 0; i < width.getValue()*height.getValue(); i++) {
            grassByDay.getItems().add(i);
        }
        grassByDay.setValue(7);
    }

    @FXML
    private void setGenomMutations() {
        maximalGenMutations.getItems().clear();
        minimalGenMutations.getItems().clear();
        for (int i = 0; i <= genLength.getValue(); i++) {
            minimalGenMutations.getItems().add(i);
            maximalGenMutations.getItems().add(i);
        }
        minimalGenMutations.setValue(2);
        maximalGenMutations.setValue(3);
    }

    @FXML
    private void setMaximalGenMutations(){
        if (minimalGenMutations.getValue() == null || maximalGenMutations.getValue() == null) return;
        if (minimalGenMutations.getValue() > maximalGenMutations.getValue()){
            maximalGenMutations.setValue(minimalGenMutations.getValue());
        }
    }

    @FXML
    private void setMinimalGenMutations(){
        if (minimalGenMutations.getValue() == null || maximalGenMutations.getValue() == null) return;
        if (minimalGenMutations.getValue() > maximalGenMutations.getValue()){
            minimalGenMutations.setValue(maximalGenMutations.getValue());
        }
    }

    private void configureStage(Stage primaryStage, HBox viewRoot){
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void startClicked() throws InterruptedException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        Stage stage = new Stage();
        HBox hBox = fxmlLoader.load();

        configureStage(stage, hBox);
        stage.show();


        MapType mapType = MapType.GLOBEMAP;
        GenomType genomType = GenomType.NORMAL_GENOM;

        if (tunnel.isSelected())
            mapType = MapType.TUNNELMAP;
        if (crazyGenom.isSelected())
            genomType = GenomType.JUMPING_GENOM;
        Simulation simulation = new Simulation(mapType, width.getValue(), height.getValue(), grassAmount.getValue(),
                grassEnergy.getValue(), grassByDay.getValue(), animalsAmount.getValue(), startEnergy.getValue(),
                energyLostEveryDay.getValue(), energyToReproduce.getValue(), reproduceEnergy.getValue(),
                minimalGenMutations.getValue(), maximalGenMutations.getValue(), genomType, genLength.getValue(),
                tunnelNumber.getValue());

        SimulationPresenter presenter = fxmlLoader.getController();
        //simulation.subscribe(presenter);
        presenter.setMap(simulation.getMap());
        WorldMap map = simulation.getMap();
        map.subscribe(presenter);
        presenter.initializePresenter(map);

        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsyncWithThreadPool();
        //simulation.run();


    }
}
