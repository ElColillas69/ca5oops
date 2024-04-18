import java.io.*;
import java.net.*;
import java.sql.*;
import com.google.gson.Gson;

public class Server {
    private static final int PORT = 12345;
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                processRequest(request, out);

                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processRequest(String request, PrintWriter out) {
        String[] parts = request.split(":");
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Students", "root", "")) {
            switch (command) {
                case "DisplayEntityById":
                    int id = Integer.parseInt(data);
                    PreparedStatement statement1 = con.prepareStatement("SELECT * FROM student_table WHERE Student_id = ?");
                    statement1.setInt(1, id);
                    ResultSet resultSet1 = statement1.executeQuery();
                    if (resultSet1.next()) {
                        student student = new student(resultSet1.getString("First_name"), resultSet1.getString("LastName"), resultSet1.getDouble("Marks"), resultSet1.getString("Address"));
                        String json = gson.toJson(student);
                        out.println(json);
                    } else {
                        out.println("Student not found");
                    }
                    break;

                case "DisplayAllEntities":
                    Statement statement2 = con.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM student_table");
                    while (resultSet2.next()) {
                        student student = new student(resultSet2.getString("First_name"), resultSet2.getString("LastName"), resultSet2.getDouble("Marks"), resultSet2.getString("Address"));
                        String json = gson.toJson(student);
                        out.println(json);
                    }
                    break;

                case "AddEntity":
                    student newStudent = gson.fromJson(data, student.class);
                    PreparedStatement statement3 = con.prepareStatement("INSERT INTO student_table (First_name, LastName, Marks, Address) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement3.setString(1, newStudent.firstname);
                    statement3.setString(2, newStudent.lastname);
                    statement3.setDouble(3, newStudent.Marks);
                    statement3.setString(4, newStudent.Address);
                    int rowsAffected = statement3.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = statement3.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            newStudent = new student(newStudent.firstname, newStudent.lastname, newStudent.Marks, newStudent.Address);
                            String json = gson.toJson(newStudent);
                            out.println(json);
                        }
                    } else {
                        out.println("Error adding student");
                    }
                    break;

                case "DeleteEntityById":
                    int deleteId = Integer.parseInt(data);
                    PreparedStatement statement4 = con.prepareStatement("DELETE FROM student_table WHERE Student_id = ?");
                    statement4.setInt(1, deleteId);
                    int rowsDeleted = statement4.executeUpdate();
                    if (rowsDeleted > 0) {
                        out.println("Student with ID " + deleteId + " deleted successfully");
                    } else {
                        out.println("Error deleting student");
                    }
                    break;

                default:
                    out.println("Invalid command");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
