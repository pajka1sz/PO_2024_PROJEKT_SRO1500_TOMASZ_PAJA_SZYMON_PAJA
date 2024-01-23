package model.map;

import model.Animal;
import model.GenomType;
import model.Plant;
import model.Vector2d;
import model.util.AnimalsListComparator;
import model.util.RandomListGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractWorldMap implements WorldMap {
    protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    public HashMap<Vector2d, List<Animal>> animals = new HashMap<>();
    private List<Vector2d> freePreferredPositions = new ArrayList<>();
    private List<Vector2d> freeNormalPositions = new ArrayList<>();
    protected List<Vector2d> allPositions = new ArrayList<>();


    protected int width;
    protected int height;
    private final int equatorLaneBottom;
    private final int equatorLaneUp;
    private final int numOfNewPlantsEachDay;
    private final int energyAddedByAPlant;
    protected int energyLostEveryDay;
    private final int energyNeededToBeReadyToReproduce;
    private final int energyLostToReproduce;
    private final int minMutations;
    private final int maxMutations;
    private final GenomType genomType;
    private final int genomLength;
    protected int animalsAlive;
    private double sumOfDeadAnimalsLifeLengths = 0;
    private double numOfDeadAnimals = 0;

    public AbstractWorldMap(int width, int height, int numOfStartPlants, int energyAddedByAPlant, int numOfNewPlantsEachDay, int numOfStartAnimals,
                            int startAnimalEnergy, int energyLostEveryDay, int energyNeededToBeReadyToReproduce, int energyLostToReproduce, int minMutations, int maxMutations,
                            GenomType genomType, int genomLength) {
        this.width = width;
        this.height = height;
        this.energyAddedByAPlant = energyAddedByAPlant;
        this.numOfNewPlantsEachDay = numOfNewPlantsEachDay;
        this.energyLostEveryDay = energyLostEveryDay;
        this.energyNeededToBeReadyToReproduce = energyNeededToBeReadyToReproduce;
        this.energyLostToReproduce = energyLostToReproduce;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genomType = genomType;
        this.genomLength = genomLength;

        this.animalsAlive = numOfStartAnimals;


        //Creating equator lane
        int numOfPreferredPositions = (int) (width * height * 0.2);
        int heightOfEquatorLane =
                height % 2 == 0 ?
                        (numOfPreferredPositions / width % 2 == 1) ?
                                (numOfPreferredPositions / width) + 1 : numOfPreferredPositions / width
                        :
                        (numOfPreferredPositions / width % 2 == 0) ?
                                (numOfPreferredPositions / width) + 1 : numOfPreferredPositions / width;

        this.equatorLaneBottom = (height - heightOfEquatorLane) / 2;
        this.equatorLaneUp =
                height % 2 == 0 ?
                        height / 2 - 1 + heightOfEquatorLane / 2
                        :
                        (height - heightOfEquatorLane) / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (j >= equatorLaneBottom && j <= equatorLaneUp)
                    freePreferredPositions.add(new Vector2d(i, j));
                else
                    freeNormalPositions.add(new Vector2d(i, j));
            }
        }

        allPositions.addAll(freePreferredPositions);
        allPositions.addAll(freeNormalPositions);

        for (int i = 0; i < numOfStartAnimals; i++) {
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            Vector2d position = new Vector2d(x, y);
            animals.computeIfPresent(position, (existingKey, existingList) -> {
                existingList.add(new Animal(genomLength, startAnimalEnergy, position, genomType));
                return existingList;
            });
            animals.computeIfAbsent(position, (existingKey) -> {
               List<Animal> existingList = new ArrayList<>();
               existingList.add(new Animal(genomLength, startAnimalEnergy, position, genomType));
               return existingList;
            });
        }
        for (List<Animal> list: animals.values()) {
//            list.sort((a, b) -> {
//                return a.getEnergy() > b.getEnergy() ? 1 : a.getEnergy() < b.getEnergy() ? -1 :
//                        a.getDaysAlive() > b.getDaysAlive() ? 1 : a.getDaysAlive() < b.getDaysAlive() ? -1 :
//                                a.getChildren() > b.getChildren() ? 1 : a.getChildren() < b.getChildren() ? -1 :
//                        ThreadLocalRandom.current().nextInt(2) == 0 ? 1 : -1;
//            });
            list.sort(new AnimalsListComparator());
        }


        //Creating new plants
        this.addStartPlants(numOfStartPlants);
    }


    public abstract boolean canMoveTo(Vector2d position);

    public void addStartPlants(int numOfNewPlants) {
        int allFreePositions = freePreferredPositions.size() + freeNormalPositions.size();

        for (int k = 0; k < numOfNewPlants && k < allFreePositions; k++) {
            double rand = Math.random();

            if (rand < 0.8) {
                if (freePreferredPositions.size() > 0) {
                    List<Vector2d> plantPosition = RandomListGenerator.getRandomValues(
                            freePreferredPositions, 1);
                    plants.put(plantPosition.get(0), new Plant(plantPosition.get(0)));
                    freePreferredPositions.remove(freePreferredPositions.size() - 1);
                }
                else {
                    List<Vector2d> plantPosition = RandomListGenerator.getRandomValues(
                            freeNormalPositions, 1);
                    plants.put(plantPosition.get(0), new Plant(plantPosition.get(0)));
                    freeNormalPositions.remove(freeNormalPositions.size() - 1);
                }
            }
            else
            {
                if (freeNormalPositions.size() > 0) {
                    List<Vector2d> plantPosition = RandomListGenerator.getRandomValues(
                            freeNormalPositions, 1);
                    plants.put(plantPosition.get(0), new Plant(plantPosition.get(0)));
                    freeNormalPositions.remove(freeNormalPositions.size() - 1);
                }
                else {
                    List<Vector2d> plantPosition = RandomListGenerator.getRandomValues(
                            freePreferredPositions, 1);
                    plants.put(plantPosition.get(0), new Plant(plantPosition.get(0)));
                    freePreferredPositions.remove(freePreferredPositions.size() - 1);
                }
            }
        }
    }

    public void addPlants() {
        addStartPlants(numOfNewPlantsEachDay);
    }

    public void eatAPlant(Animal animal, int energyAddedByAPlant) {
        if (plants.containsKey(animal.getPosition())) {
            animal.plantEaten(energyAddedByAPlant);
            plants.remove(animal.getPosition());
            if (animal.getPosition().getY() >= equatorLaneBottom && animal.getPosition().getY() <= equatorLaneUp)
                freePreferredPositions.add(animal.getPosition());
            else
                freeNormalPositions.add(animal.getPosition());
        }
    }

    public void removeDeadAnimals(int currentDay) {
        HashMap<Vector2d, List<Animal>> newHashMap = new HashMap<>();
        for (Vector2d position: animals.keySet()) {
            List<Animal> animalsOnPosition = animals.get(position);
            List<Animal> removedAnimals = new ArrayList<>();
            List<Animal> listToPut = new ArrayList<>();
            for (Animal animal: animalsOnPosition) {
                if (animal.isDead()) {
                    removedAnimals.add(animal);
                    animal.setDayOfDeath(currentDay);
                    System.out.println(animal + " " + animal.getPosition() + " " + animal.getEnergy());
                    sumOfDeadAnimalsLifeLengths += animal.getDayOfDeath();
                    numOfDeadAnimals += 1;
                    animalsAlive -= 1;
                }
            }
            for (Animal animal: animalsOnPosition) {
                if (!removedAnimals.contains(animal))
                    listToPut.add(animal);
            }
            if (listToPut.size() > 0)
                newHashMap.put(position, listToPut);
        }
        System.out.println("Już prawie");
        animals = newHashMap;
        System.out.println("Dość");
    }

    public abstract void moveAnimals();

    public void eatPlants() {
        for (Vector2d position: animals.keySet()) {
            Animal animalWhichCanEat = animals.get(position).get(0);
            eatAPlant(animalWhichCanEat, energyAddedByAPlant);
        }
    }

    public void reproduce() {
        for (List<Animal> list: animals.values()) {
            if (list.size() >= 2) {
                if (list.get(0).getEnergy() >= energyNeededToBeReadyToReproduce &&
                    list.get(1).getEnergy() >= energyNeededToBeReadyToReproduce) {
                    Animal parent1 = list.get(0);
                    Animal parent2 = list.get(1);

                    list.remove(parent1);
                    list.remove(parent2);

                    System.out.println("Energia przed rozmnażaniem: " + parent1.getEnergy() + " " + parent2.getEnergy());

                    parent1.giveBirth(energyLostToReproduce);
                    parent2.giveBirth(energyLostToReproduce);

                    list.add(parent1);
                    list.add(parent2);
                    list.add(new Animal(genomLength, energyLostToReproduce, parent1, parent2, genomType, minMutations, maxMutations));

                    list.sort(new AnimalsListComparator());

                    animals.put(parent1.getPosition(), list);
                    animalsAlive += 1;

                    System.out.println("Energia po rozmnażaniu: " + parent1.getEnergy() + " " + parent2.getEnergy());
                }
            }
        }
    }

    public HashMap<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }

    public int getAnimalsAlive() {
        return animalsAlive;
    }

    public int getNumOfPlants() {
        return plants.size();
    }

    public int getNumOfFreePositions() {
        return freePreferredPositions.size() + freeNormalPositions.size();
    }

    public double getAverageLifeLengthOfDeadAnimals() {
        return sumOfDeadAnimalsLifeLengths / numOfDeadAnimals;
    }
}
