public class Main {

    public static void main(String[] args) {

        new AppConfiguration(args[0], Integer.parseInt(args[1]), args[2]);
        RequestFactory requestFactory = new RequestFactory();
        requestFactory.requestEmail();

    }
}