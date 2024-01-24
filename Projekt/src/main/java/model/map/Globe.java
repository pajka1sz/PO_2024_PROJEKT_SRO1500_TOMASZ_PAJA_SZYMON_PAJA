package model.map;

import model.*;
import model.util.AnimalsListComparator;
import model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Globe extends AbstractWorldMap {
    public Globe(int width, int height, int numOfStartPlants, int energyAddedByAPlant, int numOfNewPlantsEachDay,  int numOfStartAnimals,
                 int startAnimalEnergy, int energyLostEveryDay, int energyNeededToBeReadyToReproduce, int energyLostToReproduce, int minMutations, int maxMutations,
                 GenomType genomType, int genomLength) {
        super(width, height, numOfStartPlants, energyAddedByAPlant, numOfNewPlantsEachDay, numOfStartAnimals, startAnimalEnergy,
                energyLostEveryDay, energyNeededToBeReadyToReproduce, energyLostToReproduce, minMutations, maxMutations, genomType, genomLength);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(position.getY() >= this.height || position.getY() < 0);
    }

    @Override
    public void moveAnimals() {
        //System.out.println(animals.keySet());
        HashMap<Vector2d, List<Animal>> newAnimalsPositions = new HashMap<>();
        for (Vector2d position: animals.keySet()) {
            List<Animal> animalsOnPosition = animals.get(position);

            for (Animal animal: animalsOnPosition) {
                //System.out.println("HALO HALO" + animal.getPosition());
                animal.move(energyLostEveryDay, this, width);
                List<Animal> listToPut = newAnimalsPositions.get(animal.getPosition()) == null ?
                        new ArrayList<>() : newAnimalsPositions.get(animal.getPosition());
                listToPut.add(animal);
                //System.out.println("Prawie" + animal.getPosition());
                newAnimalsPositions.put(animal.getPosition(), listToPut);
                //System.out.println("TUU");
                //System.out.println(newAnimalsPositions);
            }
            if (animalsOnPosition.isEmpty())
                animals.remove(position);
        }
        System.out.println(newAnimalsPositions);
        animals = new HashMap<>();
        for (Vector2d position: newAnimalsPositions.keySet()) {
            List<Animal> listToPut = newAnimalsPositions.get(position);
            listToPut.sort(new AnimalsListComparator());
            animals.put(position, listToPut);
        }
    }

    @Override
    public HashMap<Vector2d, List<WorldElement>> getElements() {
        HashMap<Vector2d, List<WorldElement>> elements = new HashMap<>();
        for (Vector2d position: animals.keySet()) {
            ArrayList<WorldElement> listToPut = new ArrayList<>();
            listToPut.add(animals.get(position).get(0));
            elements.put(position, listToPut);
        }
        for (Vector2d position: plants.keySet()) {
            List<WorldElement> listToPut = new ArrayList<>();
            if (elements.get(position) != null)
                listToPut = elements.get(position);
            listToPut.add(plants.get(position));
            elements.put(position, listToPut);
        }
        return elements;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return plants.containsKey(position) || animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position))
            return animals.get(position).get(0);
        else if (plants.containsKey(position))
            return plants.get(position);
        return null;
    }

    public String toString() {
        MapVisualizer mapping = new MapVisualizer(this);
        return mapping.draw(new Vector2d(0, 0), new Vector2d(this.width - 1, this.height - 1)) + animalsAlive;
        //return String.valueOf(animalsAlive);
    }
}
