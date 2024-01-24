package model;

import model.map.WorldMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFile {
    private WorldMap map;

    public CSVFile(WorldMap map) {
        this.map = map;
    }

    public void createCsvFile(String path) {
        try (BufferedWriter writing = new BufferedWriter(new FileWriter(path))) {
            // Nagłówki
            writing.write("Number of animals | Number of plants | Number of free places | Average animal energy | " +
                    "Average number of kids | Average length of life of dead animal | Most popular genom");
            writing.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addRowToCsv(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String newRow = map.getAnimalsAlive() + "|" +
                    map.getNumOfPlants() + "|" +
                    map.getNumOfFreePositions() + "|" +
                    map.averageAnimalEnergy() + "|" +
                    map.averageNumberOfChildren() + "|" +
                    map.getAverageLifeLengthOfDeadAnimals() + "|" +
                    map.getMostPopularGenom();
            writer.write(newRow);
            writer.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
