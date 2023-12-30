package model.genom;


public class NormalGenom extends AbstractGenom {

    public NormalGenom(int n) {
        super(n);
    }

    public NormalGenom(int n, int mutations, int energyFirst, String genomFirst, int energySecond, String genomSecond) {
        super(n, mutations, energyFirst, genomFirst, energySecond, genomSecond);
    }

    @Override
    public int getGen() {
        char result = genom.charAt(iterator);
        iterator = (iterator + 1) % genom.length();
        return result - '0';
    }
}