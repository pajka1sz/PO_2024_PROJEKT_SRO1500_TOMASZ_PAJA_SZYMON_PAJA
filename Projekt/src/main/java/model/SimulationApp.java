package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Start.fxml"));
        VBox viewRoot = loader.load();
        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, VBox viewRoot){
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation App");
        double minWidth = viewRoot.getMinWidth();
        double minHeight = viewRoot.getMinHeight();

        if (minWidth >= 0 && minHeight >= 0) {
            primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
            primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        }
        else
            System.out.println("Invalid");
    }
}
