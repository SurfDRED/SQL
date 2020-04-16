package data;

import com.github.javafaker.Faker;
import lombok.val;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QueriesDB {
    static String passwordBdOne = "$2a$10$E8Rhn6smg/H9xXLlQlVD.Op2ceJIYu0U.m/wCm9MadtVKbWrKJuLi";
    static String  passwordBdTwo = "$2a$10$NRooRlTLpc2pKQnUXAoAfe.vtF.JWbCKZL2RxXJCQ.EEpfoCEa.5.";

    public static String getPasswordBdOne() {
        return passwordBdOne;
    }

    public static String getPasswordBdTwo() {
        return passwordBdTwo;
    }

    public static void Clear() throws SQLException {
        val deleteAuthCodes = "DELETE from auth_codes";
        val deleteCards = "DELETE from cards;";
        val deleteUsers = "DELETE from users;";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                val dataStmt = conn.createStatement();
        ) {
            dataStmt.executeUpdate(deleteAuthCodes);
            dataStmt.executeUpdate(deleteCards);
            dataStmt.executeUpdate(deleteUsers);
        }
    }

    public static void AddUser() throws SQLException {
        val faker = new Faker();
        val dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                val dataStmt = conn.prepareStatement(dataSQL);
        ) {
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, faker.name().firstName());
            dataStmt.setString(3, passwordBdOne);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, faker.name().firstName());
            dataStmt.setString(3, passwordBdTwo);
            dataStmt.executeUpdate();
        }
    }
}