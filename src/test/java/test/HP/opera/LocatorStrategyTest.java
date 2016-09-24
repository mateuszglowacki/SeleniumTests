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

import java.net.URL;

public class LocatorStrategyTest {

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
    public void locatorStrategyCSS() throws Exception{
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("a")).getAttribute("href");
        System.out.println(link);
    }

    @Test
    public void locatorStrategyPreciseCSS() throws Exception{
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("#content a")).getAttribute("href");
        System.out.println(link);
    }

    @Test
    public void locatorStrategyExactCSS() throws Exception {
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("#content .example a")).getAttribute("href");
        System.out.println(link);
    }

}