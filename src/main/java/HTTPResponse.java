import java.io.BufferedWriter;
import java.io.IOException;

public class HTTPResponse {
    public int statusCode;
    public String body;

    public HTTPResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public void send(BufferedWriter outToClient) {
        try {
            outToClient.write("HTTP/1.1 " + this.statusCode + " OK\n");
            outToClient.write("Content-Length: " + this.body.length() + "\n");
            outToClient.write("\n");
            outToClient.write(body + "\n");

            outToClient.flush();
            outToClient.close();
        } catch (IOException e) {
            System.out.println("Error sending HTTP Response. Body: " + this.body);
            e.printStackTrace();
        }
    }
}
