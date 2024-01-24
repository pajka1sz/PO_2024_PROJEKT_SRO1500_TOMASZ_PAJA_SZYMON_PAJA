package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    public CheckBox tunnel;
    @FXML
    public CheckBox crazyGenom;
    @FXML
    public ComboBox<Integer> tunnelNumber;
    @FXML
    public ComboBox<Integer> reproduceEnergy;


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
        }
        grassEnergy.setValue(5);
        genLength.setValue(5);
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
    }

    @FXML
    private void setGrassAmount() {
        SpinnerValueFactory<Integer> grass = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, width.getValue()* height.getValue(), 10);
        grassAmount.setValueFactory(grass);
    }

    @FXML
    private void setGrassByDay() {
        grassByDay.getItems().clear();
        for (int i = 0; i < width.getValue()*height.getValue(); i++) {
            grassByDay.getItems().add(i);
        }
        grassByDay.setValue(15);
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
}
