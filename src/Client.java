import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello from client");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = in.readLine();
            System.out.println("Received from server: " + serverResponse);

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
