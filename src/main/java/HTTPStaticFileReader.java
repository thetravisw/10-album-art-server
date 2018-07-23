import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class HTTPStaticFileReader {
    private String path;

    public HTTPStaticFileReader(HTTPRequest request) {
        this.path = request.path;
    }

    public String getContents() throws IOException {
        String result = "";

        // How do I load a file from resource folder?
        // https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
        String filepath = "C:\\Users\\H_K_K\\codefellows\\401\\AlbumArtServer\\src\\main\\resources\\static" + this.path;
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result += processLine(line);
            }
            return result;
        }
    }

    // accepts a line and either returns the plain line, or
    // detects the template syntax {{SYMBOL_MARKER}} and replaces that portion
    // of the line with corresponding content for "SYMBOL_MARKER"
    private String processLine(String line) {
        if (!line.contains("{{")) {
            return line;
        }

        // "<p>{{RANDOM_JSON_QUOTE}}</p>"
        // first "<p>"
        // rest "RANDOM_JSON_QUOTE}}</p>"
        String[] cells = line.split("\\{\\{");
        String first = cells[0];
        String rest = cells[1];

        cells = rest.split("\\}\\}");
        String symbol = cells[0];
        String last = cells[1];

        String content = "_____________";
        if (symbol.equals("RANDOM_JSON_QUOTE")) {
            content = randomJSONQuote();
        } else if (symbol.equals("TIMESTAMP")) {
            content = currentTimestamp();
        }

        return first + content + last;
    }

    public String randomJSONQuote() {
       return "\"I am not a crook.\" --Nixon";
    }

    public String currentTimestamp() {
        Date date = new Date();
        return date.toString();
    }
}
