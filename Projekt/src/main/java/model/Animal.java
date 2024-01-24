package model;

import model.genom.Genom;
import model.genom.JumpingGenom;
import model.genom.NormalGenom;
import model.util.MoveValidator;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    private final Genom genom;


    private int energy;
    private int plantsEaten = 0;
    private int children = 0;
    private int allChildren = 0;
    private int daysAlive = 0;
    private int dayOfDeath;
    private int activeGen;
    private Animal parent1 = null;
    private Animal parent2 = null;

    //Constructor creating new, generated animal when the application starts.
    public Animal(int n, int startEnergy, Vector2d position, GenomType genomType) {
        this.energy = startEnergy;

        this.direction = MapDirection.generateDirection();
        this.position = position;
        this.genom = switch (genomType) {
            case NORMAL_GENOM -> new NormalGenom(n);
            case JUMPING_GENOM -> new JumpingGenom(n);
        };
    }

    //Constructors creating new child of two animals.
    public Animal(int n, int parentEnergyUsedToReproduce, Animal parent1, Animal parent2, GenomType genomType,
                  int mutationsMin, int mutationsMax) {
        this.energy = parentEnergyUsedToReproduce * 2;

        int mutations = ThreadLocalRandom.current().nextInt(mutationsMax - mutationsMin + 1) + mutationsMin;

        this.direction = MapDirection.generateDirection();
        this.position = parent1.getPosition();
        this.genom = switch (genomType) {
            case NORMAL_GENOM -> new NormalGenom(n, mutations, parent1.getEnergy(), parent1.getGenom(),
                    parent2.getEnergy(), parent2.getGenom());
            case JUMPING_GENOM -> new JumpingGenom(n, mutations, parent1.getEnergy(), parent1.getGenom(),
                    parent2.getEnergy(), parent2.getGenom());
        };

        this.parent1 = parent1;
        this.parent2 = parent2;

        //System.out.println(position);
    }


    //Methods
    //Method allowing the animal to move.
    public void move(int energyLostEveryDay, MoveValidator validator, int width) {
        activeGen = genom.getNextGen();
        direction = direction.rotate(activeGen);

        Vector2d newPosition = new Vector2d((position.add(getDirection().toUnitVector()).getX() + width) % width,
                position.add(getDirection().toUnitVector()).getY());

        if (validator.canMoveTo(newPosition))
            position = newPosition;
        else
            direction = direction.rotate(4);

        daysAlive += 1;
        energy -= energyLostEveryDay;

        //System.out.println(this.toString());
    }

    //Method giving energy to the animal when it eats a plant.
    public void plantEaten(int energyAddedAByAPlant) {
        energy += energyAddedAByAPlant;
        plantsEaten += 1;
    }

    //Method substracting energy needed to reproduce and adding children.
    public void giveBirth(int energyLostToReproduce) {
        //Dodać przypadek, w którym rodzic tworzy potomka wspólnie ze swoim dzieckiem.
        energy -= energyLostToReproduce;
        children += 1;
        allChildren += 1;

        if (parent1 != null && parent2 != null) {
            parent1.addGrandchild();
            parent2.addGrandchild();
        }
    }

    //Method adding a child to grandparents, grand-grandparents...
    public void addGrandchild() {
        allChildren += 1;

        if (parent1 != null && parent2 != null) {
            parent1.addGrandchild();
            parent2.addGrandchild();
            System.out.println(parent1 + " " + parent2);
        }
    }

    //Method checking whether the animal has any energy to be alive.
    public boolean isDead() {
        return energy <= 0;
    }

    //Method which sets the value to dayOfDeath attribute of dead animal.
    public void setDayOfDeath(int day) {
        dayOfDeath = day;
    }

    //For going in the tunnel
    public void throughTunnel(Vector2d newPosition) {
        System.out.println("Stara pozycja: " + this.position);
        this.position = newPosition;
        System.out.println("Nowa pozycja: " + this.position);
    }


    //Getters
    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Genom getGenom() {
        return genom;
    }


    public int getEnergy() {
        return energy;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getChildren() {
        return children;
    }

    public int getAllChildren() {
        return allChildren;
    }

    public int getDaysAlive() {
        return daysAlive;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public int getActiveGen() {
        return activeGen;
    }


    public String toString() {
        //return "Position: " + position.toString() + ", Direction: " + direction + ", Active gen: " + activeGen + ", Energy left: " + energy;
        return direction.toString();
    }
}
