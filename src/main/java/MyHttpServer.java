import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class MyHttpServer {
    public static final int PORT = 6789;

    public static void main(String argv[]) {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            serve(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serve(ServerSocket socket) throws IOException {
        System.out.println("Listening on http://localhost:" + PORT);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("waiting for request...");
            Socket connectionSocket = socket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            HTTPRequest request = new HTTPRequest(inFromClient);

            HTTPResponse response;
            try {
                    String body = route(request);
                    response = new HTTPResponse(200, body);
                } catch (FileNotFoundException e) {
                    response = new HTTPResponse(404, "Could not find " + request.path);
                } catch (IOException e) {
                    response = new HTTPResponse(500, "Internal server error");
                } catch (Exception e){
                    response = new HTTPResponse(500, "Houston we have a problem");
                }
            response.send(outToClient);

            }
            System.out.println("closed request.");
        }

    private static String route(HTTPRequest request) throws IOException {
        String body;
        int statusCode = 200;
        if(request.path.startsWith("/search")){
//            String query=request.queryParams.get("query");
            String query = HTTPRequest.GetParams(request.path);
            String url=WebScraper.getArtUrl(query);
            body = "<html><img src='" + url + "'/></html>";
        } else {
            HTTPStaticFileReader file = new HTTPStaticFileReader(request);
            body = file.getContents();
        }
        return body;
    }
}
