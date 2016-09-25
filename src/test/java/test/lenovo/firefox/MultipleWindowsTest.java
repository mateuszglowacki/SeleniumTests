package test.lenovo.firefox;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MultipleWindowsTest {

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
        capabilities.setVersion("42");
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
            System.out.println("Closing Firefox browser");
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void multipleWindows() throws Exception{
        driver.get("http://the-internet.herokuapp.com/windows");
        driver.findElement(By.cssSelector(".example a")).click();
        Object[] allWindows = driver.getWindowHandles().toArray();
        driver.switchTo().window(allWindows[0].toString());
        assertThat(driver.getTitle(), is(not("New Window")));
        driver.switchTo().window(allWindows[1].toString());
       // not necessary driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertThat(driver.getTitle(), is("New Window"));
    }

    @Test
    public void multipleWindowsRedux() throws Exception {
        driver.get("http://the-internet.herokuapp.com/windows");

        // Get initial window handle
        String firstWindow = driver.getWindowHandle();
        // Create a newWindow variable
        String newWindow = "";
        // Trigger new window to open
        driver.findElement(By.cssSelector(".example a")).click();
        // Grab all window handles
        Set<String> allWindows = driver.getWindowHandles();

        // Iterate through window handles collection
        // Find the new window handle, storing it in the newWindow variable
        for (String window : allWindows) {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (!window.equals(firstWindow)) {
                newWindow = window;
            }
        }

        // Switch to the first window & verify
        driver.switchTo().window(firstWindow);
        assertThat(driver.getTitle(), is(not(equalTo("New Window"))));

        // Switch to the new window & verify
        driver.switchTo().window(newWindow);
        assertThat(driver.getTitle(), is(equalTo("New Window")));
    }

}