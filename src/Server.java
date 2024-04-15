import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage = in.readLine();
            System.out.println("Received from client: " + clientMessage);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Message received by server: " + clientMessage);

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
