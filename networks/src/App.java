import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter URL: ");
        String inputUrl = scanner.nextLine();

        UrlLoggerService logger = new UrlLoggerService("accessible_urls.txt");
        UrlChecker checker = new UrlChecker(logger);
        checker.start(inputUrl);

        System.out.println("Done. Accessible URLs written to accessible_urls.txt.");
    }
}