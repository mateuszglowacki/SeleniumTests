package test.HP.ie;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BrokenImagesTest {

    RemoteWebDriver driver;
    JavascriptExecutor js;

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
        js = (JavascriptExecutor) driver;
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
    public void allImagesLoaded() throws IOException {
        driver.navigate().to("http://the-internet.herokuapp.com/broken_images");
        List brokenImages = new ArrayList();
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for(int image = 0; image < images.size(); image++) {
            Object result = js.executeScript("return arguments[0].complete && " +
                    "typeof arguments[0].naturalWidth != \"undefined\" && " +
                    "arguments[0].naturalWidth > 0", images.get(image));
            if (result == Boolean.FALSE) {
                brokenImages.add(images.get(image).getAttribute("src"));
                System.out.println(images.get(image).getAttribute("src"));
            }
        }

        List emptyCollection = new ArrayList();
       assertThat(brokenImages, is(emptyCollection) );
    }

}