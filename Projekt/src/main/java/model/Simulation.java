package model;

import model.map.Globe;
import model.map.MapType;
import model.map.TunnelMap;
import model.map.WorldMap;
import model.observers.MapChangeListener;

public class Simulation implements Runnable {
    private final WorldMap map;
    private MapChangeListener observer;
    private boolean threadPause = false;
    private final Object GUI_PAUSE = new Object();

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
        //mapChanged("Czas start");
    }

    private volatile boolean stopSimulation = false;

    public void stopSimulation() {
        stopSimulation = true;
    }

    public void run() {
        while (!stopSimulation && map.getAnimals().size() > 0) {
            map.removeDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            map.reproduce();
            map.addPlants();
            //mapChanged("Zmiana");
            System.out.println(map);
            map.mapChanged(map, "Ruch");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                throw new RuntimeException();
            }
        }
        System.out.println("Wszystkie zwierzaki umarły!");
    }

    public WorldMap getMap() {
        return this.map;
    }
}