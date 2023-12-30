import model.genom.JumpingGenom;
import model.genom.NormalGenom;

public class World {
    public static void main(String[] args) {
        run(args);
        NormalGenom normalGenom = new NormalGenom(8, 3, 100, "24571011",
                50, "03357742");
        //System.out.println(normalGenom.getGenom());
//        System.out.println(normalGenom.getGen() + " " + normalGenom.getGen());
        JumpingGenom jumpingGenom = new JumpingGenom(8, 3, 100, "24571011",
                50, "03357742");
        System.out.println(jumpingGenom.getGenom());
        System.out.println(jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " +
                jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " +
                jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " + jumpingGenom.getGen() + " " +
                jumpingGenom.getGen());
    }

    public static void run(String[] args) {
        String message;
        for (String arg: args) {
            message = switch (arg) {
                case "0" -> "Zwierzak idzie na północ.";
                case "1" -> "Zwierzak idzie na północny-wschód.";
                default -> "Zły kierunek!";
            };
            System.out.println(message);
        }
    }
}
