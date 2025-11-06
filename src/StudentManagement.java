package src;
import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    //  One Scanner for whole program
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice! Try Again.");
            }
        }
    }

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb",
                "root",
                "1313" // Your MySQL password
        );
    }

    public static void addStudent() {
        try (Connection con = connect()) {

            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            String query = "INSERT INTO students(name, age, department) VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, dept);

            pst.executeUpdate();
            System.out.println(" Student Added Successfully!");

        } catch (Exception e) {
            System.out.println(" Error: " + e);
        }
    }

    public static void viewStudents() {
        try (Connection con = connect()) {
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n---- Student Records ----");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("department"));
            }

        } catch (Exception e) {
            System.out.println(" Error: " + e);
        }
    }

    public static void updateStudent() {
        try (Connection con = connect()) {

            System.out.print("Enter Student ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter New Department: ");
            String dept = sc.nextLine();

            String query = "UPDATE students SET name=?, age=?, department=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, dept);
            pst.setInt(4, id);

            int count = pst.executeUpdate();
            System.out.println(count > 0 ? " Student Updated Successfully!" : " Student ID Not Found!");

        } catch (Exception e) {
            System.out.println(" Error: " + e);
        }
    }

    public static void deleteStudent() {
        try (Connection con = connect()) {

            System.out.print("Enter Student ID to Delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int count = pst.executeUpdate();
            System.out.println(count > 0 ? " Student Deleted Successfully!" : " Student ID Not Found!");

        } catch (Exception e) {
            System.out.println(" Error: " + e);
        }
    }
}
