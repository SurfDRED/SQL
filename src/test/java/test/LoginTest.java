package test;

import data.QueriesDB;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import page.DashboardPage;
import page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {
    private static DashboardPage dashboardPage;

    @BeforeAll
    static void Queries() throws SQLException {
        QueriesDB.Clear(); //Очистим базу
        QueriesDB.AddUser(); //Добавим новых пользователей
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/User.csv", numLinesToSkip = 1)
    void LoginTest(String testUser) throws SQLException {
        open("http://localhost:9999");
        val selectUser = new QueriesDB.SelectUser(testUser);
        val loginPage = new LoginPage();
        val verificationPage = loginPage.validLogin(selectUser);
        val verificationCode = new QueriesDB.CodeUser();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterAll
    static void ClearDB() throws SQLException {
        QueriesDB.Clear(); //Очистим базу
    }
}