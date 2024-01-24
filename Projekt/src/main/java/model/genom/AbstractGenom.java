package model.genom;

import model.util.RandomListGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGenom implements Genom {

    protected String genom;
    protected int iterator;

    //Constructor used while generating animals on new map.
    public AbstractGenom(int n) {
        genom = "";
        for (int i = 0; i < n; i++) {
            char newGen = generateGen();
            genom += newGen;
        }

        this.iterator = ThreadLocalRandom.current().nextInt(genom.length());
    }

    //Constructor used when two animals reproduce.
    public AbstractGenom(int n, int mutations, int energyFirst, Genom genomFirst, int energySecond, Genom genomSecond) {
        genom = "";
        String genomFirstString = genomFirst.toString();
        String genomSecondString = genomSecond.toString();

        mutations = Math.min(mutations, n);

        double fullEnergy = energyFirst + energySecond;

        double numberOfFirstGenes = energyFirst / fullEnergy * n;
        double numberOfSecondGenes = energySecond / fullEnergy * n;

        int finalNumberOfFirstGenes = (int)numberOfFirstGenes;
        int finalNumberOfSecondGenes = (int)numberOfSecondGenes;

        if (numberOfFirstGenes - (int)numberOfFirstGenes > 0.5)
            finalNumberOfFirstGenes += 1;
        else if (numberOfSecondGenes - (int)numberOfSecondGenes > 0.5)
            finalNumberOfSecondGenes += 1;


        int strongerSide = ThreadLocalRandom.current().nextInt(2);

        if (finalNumberOfFirstGenes + finalNumberOfSecondGenes < n) {
            if (strongerSide == 0)
                finalNumberOfFirstGenes += 1;
            else
                finalNumberOfSecondGenes += 1;
        }

        if ((energyFirst >= energySecond && strongerSide == 0) || (energySecond > energyFirst && strongerSide == 1)) {
            genom += genomFirstString.substring(0, finalNumberOfFirstGenes);
            genom += genomSecondString.substring(n - finalNumberOfSecondGenes, n);
        }
        else {
            genom += genomSecondString.substring(0, finalNumberOfSecondGenes);
            genom += genomFirstString.substring(n - finalNumberOfFirstGenes, n);
        }

        //The genom is created, but now we have to do some mutations.
        List<Integer> allIndexes = new ArrayList<>();
        for (int i = 0; i < n; i++)
            allIndexes.add(i);


        //Now the algorithm will randomly choose the indexes which will be muted.
        List<Integer> mutationIndexes = new ArrayList<>();
        mutationIndexes = RandomListGenerator.getRandomValues(allIndexes, mutations);

        //At the end, the algorithm will mute these indexes.

        //Since strings are immutable in Java, the algorithm firstly converts genom to an array of chars,
        //then replaces the specified character with the muted gen and then converts it back to the string.
        char[] charGenom = genom.toCharArray();
        for (int i = 0; i < mutations; i+=1)
            charGenom[mutationIndexes.get(i)] = generateMutedGen(charGenom[mutationIndexes.get(i)]);

        genom = String.valueOf(charGenom);
        this.iterator = ThreadLocalRandom.current().nextInt(genom.length());
    }

    @Override
    public char generateGen() {
        int result = ThreadLocalRandom.current().nextInt(8);
        return (char)(result + '0');
    }

    @Override
    public synchronized char generateMutedGen(char genToBeMuted) {
        System.out.println("Generowanie");
        int intGenToBeMuted = genToBeMuted - '0';
        int newGen = ThreadLocalRandom.current().nextInt(7);
        System.out.println(newGen);
        if (newGen >= intGenToBeMuted && newGen < 7)
            newGen += 1;
        System.out.println("Po zwiększeniu: " + newGen);
        return (char)(newGen + '0');
    }

    @Override
    public String toString() {
        return genom;
    }

    //For tests only
    public int getIterator() {
        return this.iterator;
    }
}
