import model.MapDirection;

public class World {
    public static void main(String[] args) {
        run(args);

        System.out.println(MapDirection.EAST.rotate(2));
        System.out.println(MapDirection.SOUTH_EAST.rotate(4));
        System.out.println(MapDirection.NORTH.rotate(9));
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
