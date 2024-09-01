import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Regex query and URL from user input
        System.out.print("Web sitesinin URL'sini girin: ");
        String url = scanner.nextLine();

        System.out.print("Aramak istediğiniz Regex ifadesini girin: ");
        String regexQuery = scanner.nextLine();

        try {
            // connection to the target website
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:129.0) Gecko/20100101 Firefox/129.0").timeout(6000).get();
            String htmlContent = doc.html();
            // System.out.println(htmlContent);
            // Regex pattern and HTML query
            Pattern pattern = Pattern.compile(regexQuery);
            Matcher matcher = pattern.matcher(htmlContent);

            boolean found = false;
            while (matcher.find()) {
                // matching pattern
                int start = matcher.start();
                // find line start
                int lineStart = htmlContent.lastIndexOf("\n", start);
                if (lineStart == -1) lineStart = 0; 
                // find line end
                int lineEnd = htmlContent.indexOf("\n", start);
                if (lineEnd == -1) lineEnd = htmlContent.length(); 

                String matchingLine = htmlContent.substring(lineStart, lineEnd);
                System.out.println("Bulunan satır: " + matchingLine.trim());
                found = true;
            }

            if (!found) {
                System.out.println("Hiçbir sonuç bulunamadı.");
            }

        } catch (IOException e) {
            System.out.println("Web sitesine bağlanırken bir hata oluştu: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
