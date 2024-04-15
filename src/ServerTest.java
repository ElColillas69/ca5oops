import static org.junit.Assert.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {
    @Test
    public void testServerCommunication() {
        Thread serverThread = new Thread(() -> Server.main(null));
        serverThread.start();

        try {
            Socket socket = new Socket("localhost", 12345);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello from client");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = in.readLine();

            assertEquals("Message received by server: Hello from client", serverResponse);

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
