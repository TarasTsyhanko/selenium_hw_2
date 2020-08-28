package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.User;
import utils.UserFileManager;

public class LoginTest extends BaseTest {

    @DataProvider(name = "usersDataProvider")
    public Object[] userCredentialsDataProvider() {
        return UserFileManager.readFile();
    }

    @Test(dataProvider = "usersDataProvider")
    public void logInToGmailAccount(User user) {
        webDriver.get(BASE_URL);
        WebElement loginInput = webDriver.findElement(By.id("identifierId"));
        loginInput.sendKeys(user.getLogin());
        WebElement nextButton = webDriver.findElement(By.cssSelector("div.VfPpkd-RLmnJb"));
        nextButton.click();
        WebElement passwordInput = new WebDriverWait(webDriver, EXPLICIT_TIME).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type='password']")));
        passwordInput.sendKeys(user.getPassword());
        nextButton = webDriver.findElement(By.cssSelector("div.VfPpkd-RLmnJb"));
        nextButton.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").toString().equals("complete"));
        webDriver.findElement(By.cssSelector("a.gb_D.gb_Ua.gb_i")).click();
        WebElement userName = webDriver.findElement(By.cssSelector("div.gb_ub.gb_vb"));
        String userNameText = userName.getText();
        Assert.assertEquals(userNameText, user.getFullName()
                , String.format("Expected value [%s], but actual is [%s]", user.getFullName(), userNameText));

    }
}
