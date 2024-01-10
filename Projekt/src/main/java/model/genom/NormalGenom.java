package model.genom;


public class NormalGenom extends AbstractGenom {

    //For tests only
    public NormalGenom(String createdGenom) {
        super(createdGenom);
    }

    public NormalGenom(int n) {
        super(n);
    }

    public NormalGenom(int n, int mutations, int energyFirst, Genom genomFirst, int energySecond, Genom genomSecond) {
        super(n, mutations, energyFirst, genomFirst, energySecond, genomSecond);
    }

    @Override
    public int getGen() {
        char result = genom.charAt(iterator);
        iterator = (iterator + 1) % genom.length();
        return result - '0';
    }
}