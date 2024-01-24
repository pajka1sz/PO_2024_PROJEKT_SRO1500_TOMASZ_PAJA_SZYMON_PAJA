package model.map;

import model.*;
import model.util.AnimalsListComparator;
import model.util.MapVisualizer;
import model.util.RandomListGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TunnelMap extends AbstractWorldMap {
    HashMap<Vector2d, Tunnel> entries = new HashMap<>();
    HashMap<Vector2d, Tunnel> exits = new HashMap<>();
    HashMap<Vector2d, Vector2d> tunnels = new HashMap<>();

    public TunnelMap(int width, int height, int numOfStartPlants, int energyAddedByAPlant, int numOfNewPlantsEachDay, int numOfStartAnimals,
                     int startAnimalEnergy, int energyLostEveryDay, int energyNeededToBeReadyToReproduce, int energyLostToReproduce, int minMutations, int maxMutations,
                     GenomType genomType, int genomLength, int numOfTunnels) {
        super(width, height, numOfStartPlants, energyAddedByAPlant, numOfNewPlantsEachDay,numOfStartAnimals,
                startAnimalEnergy, energyLostEveryDay,energyNeededToBeReadyToReproduce, energyLostToReproduce,
                minMutations, maxMutations, genomType, genomLength);

        List<Vector2d> tunnelsPositions = RandomListGenerator.getRandomValues(allPositions,
                numOfTunnels * 2);
        for (int i = 0; i < numOfTunnels; i++) {
            entries.put(tunnelsPositions.get(i*2), new Tunnel(tunnelsPositions.get(i)));
            exits.put(tunnelsPositions.get(i*2+1), new Tunnel(tunnelsPositions.get(i*2+1)));
            tunnels.put(tunnelsPositions.get(i*2), tunnelsPositions.get(i*2+1));
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(position.getX() < 0 || position.getX() >= width ||
                position.getY() < 0 || position.getY() >= height);
    }

    @Override
    public void moveAnimals() {
        HashMap<Vector2d, List<Animal>> newAnimalsPositions = new HashMap<>();
        for (Vector2d position: animals.keySet()) {
            List<Animal> animalsOnPosition = animals.get(position);

            for (Animal animal: animalsOnPosition) {
                //System.out.println("HALO HALO" + animal.getPosition());
                animal.move(energyLostEveryDay, this, width);
                if (entries.containsKey(animal.getPosition()))
                    animal.throughTunnel(tunnels.get(animal.getPosition()));
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
        for (Vector2d position: entries.keySet()) {
            List<WorldElement> listToPut = new ArrayList<>();
            if (elements.get(position) != null)
                listToPut = elements.get(position);
            listToPut.add(entries.get(position));
            elements.put(position, listToPut);
        }
        for (Vector2d position: exits.keySet()) {
            List<WorldElement> listToPut = new ArrayList<>();
            if (elements.get(position) != null)
                listToPut = elements.get(position);
            listToPut.add(exits.get(position));
            elements.put(position, listToPut);
        }
        return elements;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || plants.containsKey(position)
                || entries.containsKey(position) || exits.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position))
            return animals.get(position).get(0);
        if (tunnels.containsKey(position))
            return entries.get(position);
        if (tunnels.containsValue(position))
            return exits.get(position);
        if (plants.containsKey(position))
            return plants.get(position);
        return null;
    }

    public String toString() {
        MapVisualizer mapping = new MapVisualizer(this);
        return mapping.draw(new Vector2d(0, 0), new Vector2d(width-1, height-1));
    }
}
