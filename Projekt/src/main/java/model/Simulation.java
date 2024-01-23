package model;

import model.map.Globe;
import model.map.MapType;
import model.map.TunnelMap;
import model.map.WorldMap;

public class Simulation {
    private final WorldMap map;
    private int currentDay = 1;

    public Simulation(MapType mapType, int width, int height, int numOfStartPlants, int energyAddedByAPlant,
                      int numOfNewPlantsEachDay, int numOfStartAnimals, int startAnimalEnergy, int energyLostEveryDay,
                      int energyNeededToBeReadyToReproduce, int energyLostToReproduce, int minMutations, int maxMutations,
                      GenomType genomType, int genomLength, int numOfTunnels) {
        map = switch (mapType) {
            case GLOBEMAP -> new Globe(width, height, numOfStartPlants, energyAddedByAPlant, numOfNewPlantsEachDay,
                    numOfStartAnimals, startAnimalEnergy, energyLostEveryDay, energyNeededToBeReadyToReproduce,
                    energyLostToReproduce, minMutations, maxMutations, genomType, genomLength);
            case TUNNELMAP -> new TunnelMap(width, height, numOfStartPlants, energyAddedByAPlant, numOfNewPlantsEachDay,
                    numOfStartAnimals, startAnimalEnergy, energyLostEveryDay, energyNeededToBeReadyToReproduce,
                    energyLostToReproduce, minMutations, maxMutations, genomType, genomLength, numOfTunnels);
        };
    }

    public synchronized void run() {
        while (map.getAnimals().size() > 0) {
            map.removeDeadAnimals(currentDay);
            map.moveAnimals();
            map.eatPlants();
            map.reproduce();
            map.addPlants();
            currentDay += 1;
            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                throw new RuntimeException();
            }
        }
        System.out.println("Wszystkie zwierzaki umarły!");
    }
}
