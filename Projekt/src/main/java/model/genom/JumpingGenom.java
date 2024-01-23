package model.genom;

import java.util.concurrent.ThreadLocalRandom;

public class JumpingGenom extends AbstractGenom {

    public JumpingGenom(int n) {
        super(n);
    }

    public JumpingGenom(int n, int mutations, int energyFirst, Genom genomFirst, int energySecond, Genom genomSecond) {
        super(n, mutations, energyFirst, genomFirst, energySecond, genomSecond);
    }

    @Override
    public int getNextGen() {
        double rand = Math.random();
        System.out.println(rand);
        if (rand < 0.8) {
            int gen = genom.charAt(iterator) - '0';
            iterator = (iterator + 1) % genom.length();
            return gen;
        }
        else {
            if (genom.length() == 1)
                return genom.charAt(0) - '0';

            int[] indexes = new int[genom.length() - 1];

            int howManyIndexesAlready = 0;
            for (int i = 0; i < genom.length(); i++) {
                if (i != iterator) {
                    indexes[howManyIndexesAlready] = i;
                    howManyIndexesAlready += 1;
                }
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(genom.length() - 1);
            iterator = indexes[randomIndex];
            iterator = (iterator + 1) % genom.length();
            System.out.println("iterator: " + iterator);
            return genom.charAt(indexes[randomIndex]) - '0';
        }
    }
}
