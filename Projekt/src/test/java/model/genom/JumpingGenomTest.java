package model.genom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JumpingGenomTest {
    @Test
    public void testGenerateGen() {
        char[] allPossibleGens = {'0', '1', '2', '3', '4', '5', '6', '7'};
        Genom genom = new JumpingGenom(4);
        char gen = genom.generateGen();
        for (char genFromArray: allPossibleGens) {
            if (genFromArray == gen) {
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @Test
    public void testGenerateMutedGen() {
        char[] allPossibleGens = {'0', '1', '2', '3', '4', '5', '6', '7'};
        Genom genom = new JumpingGenom(4);
        char gen = genom.generateMutedGen(genom.toString().charAt(0));
        for (char genFromArray: allPossibleGens) {
            if (genFromArray == gen && gen != genom.toString().charAt(0)) {
                System.out.println(gen + " " + genom.toString().charAt(0));
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @Test
    public void testGetGen() {
        JumpingGenom genom = new JumpingGenom(4, 2, 50, new NormalGenom(4), 150, new NormalGenom(4));

        assertEquals((int) genom.toString().charAt(genom.getIterator()) - 48, genom.getGen());
    }
}
