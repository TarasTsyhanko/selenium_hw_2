package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CookieFileManager;

import java.util.List;
import java.util.Random;

import static utils.Constants.FIRST_USER_COOKIE_FILE_PATH;
import static utils.Constants.SECOND_USER_COOKIE_FILE_PATH;

public class MessageTest extends BaseTest {
    private static final int EXPLICIT_TIME = 20;


    @DataProvider(name = "CookiesDataProvider")
    public Object[] cookiesFileDataProvider() {
        return new Object[]{FIRST_USER_COOKIE_FILE_PATH, SECOND_USER_COOKIE_FILE_PATH};
    }

    @Test(dataProvider = "CookiesDataProvider")
    public void sendMessageToAnotherUserTestCase(String filePath) {
        List<Cookie> cookieList = CookieFileManager.readFile(filePath);
        webDriver.navigate().to(BASE_URL);
        cookieList.forEach(cookie -> webDriver.manage().addCookie(cookie));
        webDriver.get(BASE_URL);
        WebElement compose = webDriver.findElement(By.cssSelector(".T-I.T-I-KE.L3"));
        compose.click();
        WebElement recipientsInput = webDriver.findElement(By.name("to"));
        recipientsInput.sendKeys("tarastsyhanko@gmail.com");
        WebElement subjectInput = webDriver.findElement(By.className("aoT"));
        String subjectId = String.valueOf(new Random().nextInt(10000 + 1) + 100);
        subjectInput.sendKeys(subjectId);
        WebElement massageInput = webDriver.findElement(By.cssSelector(".Ar.Au .editable"));
        massageInput.sendKeys("bala-bala-bala" + subjectId);

        WebElement sendMassageButton = webDriver.findElement(By.cssSelector("div.dC"));
        sendMassageButton.click();
        new WebDriverWait(webDriver, EXPLICIT_TIME)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Лист надіслано.']")));
        WebElement sentMassagesButton = webDriver.findElement(By.cssSelector("div.TK .aim:nth-child(4)"));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", sentMassagesButton);
        new WebDriverWait(webDriver, EXPLICIT_TIME).until(ExpectedConditions.visibilityOf(sentMassagesButton));
        sentMassagesButton.click();
        List<WebElement> subjectMassageList = new WebDriverWait(webDriver, EXPLICIT_TIME)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[text()='Кому: ']//ancestor::tbody//*[@class='bog']//span")));
        boolean isMessagePresent = subjectMassageList.stream().anyMatch(webElement -> webElement.getText().equals(subjectId));
        Assert.assertTrue(isMessagePresent
                , String.format("Expected that present message with subject [%s] ,but not result false", subjectId));

    }
}
