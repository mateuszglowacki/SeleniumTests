package test.VM.firefox;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PageObjectTest {

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

    @Test
    public void workWithBasicAuthTest() {
        GoogleSearch google = new GoogleSearch(driver);
       // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        google.searchFor("pp");
       // boolean result = google.searchResultPresent("Politechnika Poznańska");
        google.searchResultPresent("Politechnika");
        //Assert.assertTrue (result == true);
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
}

class GoogleSearch {

    private final String BASE_URL = "http://www.google.com";
    WebDriver driver;

    By searchBox = By.name("q");
    By searchBoxSubmit = By.xpath("//button[@name='btnG']");
    By topSearchResult = By.cssSelector("#rso .g");

    public GoogleSearch(WebDriver _driver) {
        this.driver = _driver;
        visit();
        Assert.assertTrue(verifyPage() == true);
    }

    public void visit() {
        driver.get(this.BASE_URL);
    }

    public void searchFor(String searchTerm) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(searchTerm);
        driver.findElement(searchBoxSubmit).click();
        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        // driver.findElement(searchBox).submit();
    }

    public void searchResultPresent(String searchResult) {
        waitFor(topSearchResult);
        Assert.assertTrue(driver.findElement(topSearchResult).getText().contains(searchResult) == true);
    }

    public void waitFor(By locator) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean verifyPage() {
        return driver.getCurrentUrl().contains("google");
    }
}