import static org.junit.Assert.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;


public class ClientTest {
    @Test
    public void testClientCommunication() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage = in.readLine();

            assertEquals("Hello from client", clientMessage);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Message received by server: " + clientMessage);

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
