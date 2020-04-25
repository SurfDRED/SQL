package page;

import data.QueriesDB;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public VerificationPage validLogin(QueriesDB.SelectUser selectUser) {
        $("[data-test-id=login] input").setValue(selectUser.getUser());
        $("[data-test-id=password] input").setValue(selectUser.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }
}
