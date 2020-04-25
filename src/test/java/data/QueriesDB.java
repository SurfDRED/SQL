package data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.sql.DriverManager;
import java.sql.SQLException;

public class QueriesDB {
    private static String passwordBdOne = "$2a$10$E8Rhn6smg/H9xXLlQlVD.Op2ceJIYu0U.m/wCm9MadtVKbWrKJuLi";
    private static String  passwordBdTwo = "$2a$10$NRooRlTLpc2pKQnUXAoAfe.vtF.JWbCKZL2RxXJCQ.EEpfoCEa.5.";
    private static String  passwordBd = "";

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
            dataStmt.setString(2, "Louie");
            dataStmt.setString(3, passwordBdOne);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, "Julee");
            dataStmt.setString(3, passwordBdTwo);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, "SurfDRED");
            dataStmt.setString(3, passwordBdTwo);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, "TestUser");
            dataStmt.setString(3, passwordBdOne);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, "Kristina");
            dataStmt.setString(3, passwordBdTwo);
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.bothify("##??#???-?##?-##??-####-###??#?#??##"));
            dataStmt.setString(2, "BadDog");
            dataStmt.setString(3, passwordBdOne);
            dataStmt.executeUpdate();
        }
    }

    @Value
    public static class SelectUser {
        private static String  User = "";
        private static String  password = "";

        public SelectUser(String loginUser) throws SQLException{
            val usersSQL = "SELECT id, login, password FROM users WHERE login = \"" + loginUser + "\";";
            try (
                    val conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );
                    val usersStmt = conn.prepareStatement(usersSQL);
            ) {
                try (val rs = usersStmt.executeQuery()) {
                    while(rs.next()) {
                        User = rs.getString("login");
                        passwordBd = rs.getString("password");
                        if (passwordBdOne.equals(passwordBd)) {
                            password = "qwerty123";
                        }
                        if (passwordBdTwo.equals(passwordBd)) {
                            password = "123qwerty";
                        }
                    }
                }
            }
        }

        public static String getUser() {
            return User;
        }

        public static String getPassword() {
            return password;
        }

    }

    @Value
    public static class CodeUser {
        private static String  User = "";
        private static String  auth_code = "";

        public CodeUser() throws SQLException{
            User = SelectUser.getUser();
            val codeSQL = "SELECT u.id, u.login, a.user_id, a.code FROM auth_codes a, users u WHERE a.user_id=u.id AND u.login=? ORDER BY a.created DESC LIMIT 1;";
            try (
                    val conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );
                    val codeStmt = conn.prepareStatement(codeSQL);
            ) {
                codeStmt.setString(1, User);
                try (val code = codeStmt.executeQuery()) {
                    while (code.next()) {
                        auth_code = code.getString("code");
                    }
                }
            }
        }
        public static String getAuth_code() {
            return auth_code;
        }
    }
}