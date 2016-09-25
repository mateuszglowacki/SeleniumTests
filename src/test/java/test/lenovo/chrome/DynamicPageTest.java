package test.lenovo.chrome;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class DynamicPageTest {

    RemoteWebDriver driver;

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
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("53");
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability("applicationName", "PCWIN8_1_32bit");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capabilities.getBrowserName() + ", Version: " + capabilities.getVersion() + ", Platform: " + capabilities.getPlatform());
    }


    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing Chrome browser");
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