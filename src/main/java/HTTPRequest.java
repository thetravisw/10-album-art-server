import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {
    public String path;
//    public String queryParams;

    public HTTPRequest(BufferedReader inFromClient) {
        processRequest(inFromClient);
    }

    public void processRequest(BufferedReader inFromClient) {
        try {
            // peel off the first GET/POST PATH line
            // "GET /home.html HTTP/1.1"
            // ["GET", "/home.html", "HTTP/1.1"][1]
            String requestLine = inFromClient.readLine();
            this.path = requestLine.split(" ")[1];

            if (this.path.equals("/")) {
                this.path = "\\index.html";
            }
        } catch (IOException e) {
            System.out.println("Error parsing HTTP request: " + this.path);
        }
    }

    public static String GetParams (String query){
        Map<String, String> params = ChopURL(query);
        return params.toString();
    }

    private static Map<String, String> ChopURL (String query){
        Map<String, String> params = new HashMap<>();
        if (!query.contains("?")) return params;

        String queries = query.split("\\?")[1];
        String[] cells = queries.split("&");
        for(String kv : cells){
            String key = cells[0];
            String val = "";
            if (cells.length>1) val=cells[1];
        }
        return params;
    }
}
