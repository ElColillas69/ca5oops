import java.io.*;
import java.net.*;
import java.util.Scanner;
import com.google.gson.Gson;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Select an option:");
                System.out.println("1. Display Entity by ID");
                System.out.println("2. Display all Entities");
                System.out.println("3. Add an Entity");
                System.out.println("4. Delete Entity by ID");
                System.out.println("5. Get Images List");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter ID:");
                        int id = scanner.nextInt();
                        displayEntityById(id);
                        break;

                    case 2:
                        displayAllEntities();
                        break;

                    case 3:
                        addEntity(scanner);
                        break;

                    case 4:
                        System.out.println("Enter ID of the entity to delete:");
                        int idToDelete = scanner.nextInt();
                        deleteEntityById(idToDelete);
                        break;

                    case 5:
                        getImagesList();
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        notifyServerOnExit();
                        return;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayEntityById(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("DisplayEntityById:" + id);

            String response = in.readLine();
            System.out.println("Entity details: " + response);
        }
    }

    private static void displayAllEntities() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("DisplayAllEntities");

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Entity details: " + response);
            }
        }
    }

    private static void addEntity(Scanner scanner) throws IOException {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter marks:");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter address:");
        String address = scanner.nextLine();

        student newStudent = new student(firstName, lastName, marks, address);

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String json = new Gson().toJson(newStudent);
            out.println("AddEntity:" + json);

            String response = in.readLine();
            System.out.println("Server response: " + response);
        }
    }

    private static void deleteEntityById(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("DeleteEntityById:" + id);

            String response = in.readLine();
            System.out.println("Server response: " + response);
        }
    }

    private static void getImagesList() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("GetImagesList");

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Image file name: " + response);
            }
        }
    }

    private static void notifyServerOnExit() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Exit");
        }
    }
}
