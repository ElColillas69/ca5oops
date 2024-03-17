import java.util.List;
import java.util.Scanner;
public class MainProgram {
    public static void main (String[] args) {
        String url = "jdbc:mysql://localhost:3306/Students";
        String username = "root";
        String password = "";
         interfaceDao studuntdao  = new  studentdaoimp(url, username, password);


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("menu");
            System.out.println("1: view students");
            System.out.println("2:add new student");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<student> students = studentDAO.getAllstudents();
                    for (student student : students) {
                        System.out.println(student);
                    }
                    break;

                case 2:
                    System.out.println("enter student id");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();

                    student foundStudent = studentDAO.getstudentById(studentId);
                    if (foundStudent != null) {
                        System.out.println("found student");
                        System.out.println(foundStudent);

                    }else{
                        System.out.println("student not found" + studentId);

                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        }

    }}