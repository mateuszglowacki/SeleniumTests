package test.HP.opera;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.*;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BrokenImagesTest {

    WebDriver driver;
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
        System.setProperty("webdriver.opera.driver", "C:/HP_environment/operadriver.exe");
        driver = new OperaDriver();
        driver.manage().window().maximize();
        System.out.println("Browser name: Opera, Version: 39, Platform: WINDOWS");
        js = (JavascriptExecutor) driver;
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