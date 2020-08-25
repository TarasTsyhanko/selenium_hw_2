package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class GmailTest extends BaseTest {
    private static final int EXPLICIT_TIME = 20;

    @Test(description = "open gmail test account and send massage")
    public void gmailSendMassageTest() {
        webDriver.get("https://mail.google.com/");
        WebElement loginInput = webDriver.findElement(By.id("identifierId"));
        loginInput.sendKeys("seleniumtsyhanko@gmail.com");
        WebElement nextButton = webDriver.findElement(By.cssSelector("div.VfPpkd-RLmnJb"));
        nextButton.click();
        WebElement passwordInput = new WebDriverWait(webDriver, EXPLICIT_TIME).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type='password']")));
        passwordInput.sendKeys("Selenium021");
        nextButton = webDriver.findElement(By.cssSelector("div.VfPpkd-RLmnJb"));
        nextButton.click();
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
