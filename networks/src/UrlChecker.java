import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Scanner;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UrlChecker {
    private Set<String> visited = new HashSet<>();
    private UrlLoggerService logger;

    public UrlChecker(UrlLoggerService logger) {
        this.logger = logger;
    }

    public void start(String rootUrl) {
        checkUrl(rootUrl);
    }

    private void checkUrl(String urlStr) {
        if (visited.contains(urlStr)) return;
        visited.add(urlStr);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 400) {
                logger.logUrl(urlStr);
                System.out.println("Accessible: " + urlStr);

                // Read HTML and extract links
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    pageContent.append(line);
                }

                reader.close();
                extractAndCheckLinks(pageContent.toString(), urlStr);
            }

        } catch (IOException e) {
            // Ignored: inaccessible or bad URL
        }
    }

    private void extractAndCheckLinks(String html, String baseUrl) {
        Pattern pattern = Pattern.compile("href=[\"'](http[^\"'#]+)[\"']", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String foundUrl = matcher.group(1);
            checkUrl(foundUrl);
        }
    }
}