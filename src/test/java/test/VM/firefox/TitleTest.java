package test.VM.firefox;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class TitleTest {

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
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setVersion("44");
        capabilities.setPlatform(Platform.LINUX);
        capabilities.setCapability("applicationName", "PCUBUNTU");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capabilities.getBrowserName() + ", Version: " + capabilities.getVersion() + ", Platform: " + capabilities.getPlatform());
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing Firefox browser");
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void mainPageTitle() throws Exception {
        driver.navigate().to("http://the-internet.herokuapp.com");
        Assert.assertEquals("Title check failed!", "The Internet", driver.getTitle());
    }

}
