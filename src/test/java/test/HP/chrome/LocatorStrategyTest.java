package test.HP.chrome;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class LocatorStrategyTest {

    RemoteWebDriver driver;

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
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
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing Chrome browser");
            driver.close();
            driver.quit();
        }
        System.out.println("########## ");
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