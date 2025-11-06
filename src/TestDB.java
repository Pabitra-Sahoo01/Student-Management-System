package src;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb",
                "root",
                "1313"   // your MySQL password (if any)
            );
            System.out.println("✅ Connected Successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }
}

