package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.QueriesDB;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }
    public DashboardPage validVerify(QueriesDB.CodeUser codeUser) {
        codeField.setValue(codeUser.getAuth_code());
        verifyButton.click();
        return new DashboardPage();
    }
}