import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UrlLoggerService {
    private String filename;

    public UrlLoggerService(String filename) {
        this.filename = filename;
    }

    public void logUrl(String url) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(url);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
        }
    }
}