package model.util;

import model.Animal;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalsListComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        return animal1.getEnergy() > animal2.getEnergy() ? 1 : animal1.getEnergy() < animal2.getEnergy() ? -1 :
                animal1.getDaysAlive() > animal2.getDaysAlive() ? 1 : animal1.getDaysAlive() < animal2.getDaysAlive() ? -1 :
                        animal1.getChildren() > animal2.getChildren() ? 1 : animal1.getChildren() < animal2.getChildren() ? -1 :
                                ThreadLocalRandom.current().nextInt(2) == 0 ? 1 : -1;
    }
}
