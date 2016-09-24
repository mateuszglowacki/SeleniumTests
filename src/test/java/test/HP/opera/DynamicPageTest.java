package test.HP.opera;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class DynamicPageTest {

    WebDriver driver;

    @BeforeClass
    public static void displayLine() {
        System.out.println("===========================================================================================");
    }

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting: " + getClass());
        }
    };

    @Before
    public void setUp() throws Exception {
        // Initialize
        System.setProperty("webdriver.opera.driver", "C:/HP_environment/operadriver.exe");
        driver = new OperaDriver();
        driver.manage().window().maximize();
        System.out.println("Browser name: Opera, Version: 39, Platform: WINDOWS");
    }


    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing Opera browser");
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void noSuchElementErrorTest() throws Exception {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void webDriverWait8Test() throws Exception {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void webDriverWaitTimeOutErrorTest() throws Exception {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void cleanUpTest() throws Exception {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

}