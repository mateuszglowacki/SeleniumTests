package test.HP.ie.auth;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkWithBasicAuth2Test {

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
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setBrowserName("internet explorer");
        capabilities.setVersion("11");
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability("applicationName", "PCWIN10_64bit");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capabilities.getBrowserName() + ", Version: " + capabilities.getVersion() + ", Platform: " + capabilities.getPlatform());
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing IE browser");
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void workWithBasicAuthTest2() throws Exception {
        driver.get("http://the-internet.herokuapp.com/basic_auth");
        String pageMessage = driver.findElement(By.cssSelector("p")).getText();
        assertThat(pageMessage, containsString("Congratulations! You must have the proper credentials."));
    }

}