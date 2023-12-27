public class World {
    public static void main(String[] args) {
        run(args);
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
