package test.HP.chrome;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaScriptTest {

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
        capabilities.setCapability("applicationName", "PCWIN10_64bit");
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
    public void JavaScriptAlertTest() throws Exception{
        driver.get("http://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.cssSelector(".example li:nth-child(2) button")).click();
        Alert popup = driver.switchTo().alert();
        popup.accept();
        String result = driver.findElement(By.id("result")).getText();
        assertThat(result, is(equalTo("You clicked: Ok")));
    }

}