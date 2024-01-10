package model.genom;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGenom implements Genom {

    protected String genom;
    protected int iterator;


    //For tests only.
    public AbstractGenom(String createdGenom) {
        genom = createdGenom;
        iterator = 0;
    }

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

        double fullEnergy = energyFirst + energySecond;

        double numberOfFirstGenes = energyFirst / fullEnergy * n;
        double numberOfSecondGenes = energySecond / fullEnergy * n;
        System.out.println(numberOfFirstGenes + " " + numberOfSecondGenes);

        int finalNumberOfFirstGenes = (int)numberOfFirstGenes;
        int finalNumberOfSecondGenes = (int)numberOfSecondGenes;

        if (numberOfFirstGenes - (int)numberOfFirstGenes > 0.5)
            finalNumberOfFirstGenes += 1;
        else if (numberOfSecondGenes - (int)numberOfSecondGenes > 0.5)
            finalNumberOfSecondGenes += 1;

        System.out.println(finalNumberOfFirstGenes + " " + finalNumberOfSecondGenes);

        int strongerSide = ThreadLocalRandom.current().nextInt(2);
        System.out.println(strongerSide);
        if ((energyFirst >= energySecond && strongerSide == 0) || (energySecond > energyFirst && strongerSide == 1)) {
            genom += genomFirstString.substring(0, finalNumberOfFirstGenes);
            genom += genomSecondString.substring(n - finalNumberOfSecondGenes, n);
        }
        else {
            genom += genomSecondString.substring(0, finalNumberOfSecondGenes);
            genom += genomFirstString.substring(n - finalNumberOfFirstGenes, n);
        }

        //The genom is created, but now we have to do some mutations.
        int[] allIndexes = new int[n];
        for (int i = 0; i < n; i++)
            allIndexes[i] = i;

        //Now the algorithm will randomly choose the indexes which will be muted.
        int[] mutationIndexes = new int[mutations];
        if (mutations >= n) {
            for (int i = 0; i < n; i++)
                mutationIndexes[i] = i;
        }
        else {
            int count = n;

            for (int i = 0; i < mutations; i++) {
                int index = ThreadLocalRandom.current().nextInt(count);
                count -= 1;
                mutationIndexes[i] = allIndexes[index];
                allIndexes[index] = allIndexes[count];
            }
        }
        System.out.println(Arrays.toString(mutationIndexes));

        //At the end, the algorithm will mute these indexes.

        //Since strings are immutable in Java, the algorithm firstly converts genom to an array of chars,
        //then replaces the specified character with the muted gen and then converts it back to the string.
        char[] charGenom = genom.toCharArray();
        for (int i = 0; i < mutations; i++)
            charGenom[mutationIndexes[i]] = generateMutedGen(charGenom[mutationIndexes[i]]);
        genom = String.valueOf(charGenom);

        this.iterator = ThreadLocalRandom.current().nextInt(genom.length());
    }

    @Override
    public char generateGen() {
        int result = ThreadLocalRandom.current().nextInt(8);
        return (char)(result + '0');
    }

    @Override
    public char generateMutedGen(char genToBeMuted) {
        int intGenToBeMuted = genToBeMuted - '0';
        int newGen = ThreadLocalRandom.current().nextInt(7);
        if (newGen >= intGenToBeMuted)
            newGen += 1;
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
