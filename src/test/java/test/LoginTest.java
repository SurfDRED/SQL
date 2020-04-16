package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.QueriesDB;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static data.QueriesDB.*;

public class LoginTest {
    private SelenideElement UserLogin = $("[data-test-id=login] input");
    private SelenideElement UserPassword = $("[data-test-id=password] input");
    private SelenideElement btnUserClick = $("[data-test-id=password] input");
    private SelenideElement UserCode = $("[data-test-id=code] input");
    private SelenideElement btnUserVerify = $("[data-test-id=action-verify]");
    private SelenideElement dashboard = $("[data-test-id=dashboard]");
    private String password = "";

    @BeforeAll
    static void Queries() throws SQLException {
        QueriesDB.Clear(); //Очистим базу
        QueriesDB.AddUser(); //Добавим новых пользователей
    }

    @AfterAll
    static void ClearDB() throws SQLException {
        QueriesDB.Clear(); //Очистим базу
    }

    @Test
    void LoginTest() throws SQLException {
        val usersSQL = "SELECT id, login, password FROM users;";
        val codeSQL = "SELECT u.id, u.login, a.user_id, a.code FROM auth_codes a, users u WHERE a.user_id=u.id AND u.login=? ORDER BY a.created DESC LIMIT 1;";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                val usersStmt = conn.prepareStatement(usersSQL);
                val codeStmt = conn.prepareStatement(codeSQL);
        ) {
            try (val rs = usersStmt.executeQuery()) {
                while(rs.next()) {
                    val login = rs.getString("login");
                    val passwordBd = rs.getString("password");
                    if (getPasswordBdOne().equals(passwordBd)) {
                        password = "qwerty123";
                    }
                    if (getPasswordBdTwo().equals(passwordBd)) {
                        password = "123qwerty";
                    }
                    open("http://localhost:9999");
                    UserLogin.setValue(login);
                    UserPassword.setValue(password);
                    btnUserClick.click();
                    codeStmt.setString(1, login);
                    try (val code = codeStmt.executeQuery()) {
                        while (code.next()) {
                            val auth_code = code.getString("code");
                            UserCode.setValue(auth_code);
                            btnUserVerify.click();
                            dashboard.shouldBe(Condition.visible);
                        }
                    }
                }
            }
        }
    }
}