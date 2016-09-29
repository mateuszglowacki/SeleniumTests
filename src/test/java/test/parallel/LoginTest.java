package test.parallel;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import test.parallel.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(test.parallel.Parallelized.class)
public class LoginTest {

    //Declare DesiredCapabilities configuration variables
    private String platform; //Use if you need to use platform
    private String os;
    private String browserName;
    private String browserVersion;

    //Hold all Configuration values in a LinkedList
    @Parameterized.Parameters
    public static LinkedList<String[]> getEnvironments() throws Exception {
        LinkedList<String[]> env = new LinkedList<String[]>();
        env.add(new String[]{"WINDOWS", "chrome", "53"});
        env.add(new String[]{"WINDOWS", "internet explorer", "11"});
        //add more browsers here
        return env;
    }

    //Constructor
    public LoginTest(String platform, String browserName, String browserVersion) {
        this.platform = platform;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

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
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("platform", platform);
        capability.setCapability("browserName", browserName);
        capability.setCapability("version", browserVersion);
        capability.setCapability("build", "JUnit-Parallel");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capability);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capability.getBrowserName() + ", Version: " + capability.getVersion() + ", Platform: " + capability.getPlatform());
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void withValidCredentials() throws IOException {
        driver.navigate().to("http://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        assertThat(successMessage.isDisplayed(), is(Boolean.TRUE));
    }

    @Test
    public void withInvalidCredentials() throws IOException {
        driver.navigate().to("http://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("test124");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.cssSelector("button")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement errorMessage = driver.findElement(By.cssSelector(".flash.error"));
        assertThat(errorMessage.isDisplayed(), is(Boolean.TRUE));
    }


}