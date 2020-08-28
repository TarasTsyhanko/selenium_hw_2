package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver webDriver;
    protected static final String BASE_URL="https://mail.google.com";
    protected static final Integer EXPLICIT_TIME = 20;

    @BeforeMethod
    public void setUp(){
        log.info("create chrome driver");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        log.info("chrome was started successfully");
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(){
        log.info("try to quit driver");
        webDriver.quit();
        log.info("driver was quit successfully");
    }
}
