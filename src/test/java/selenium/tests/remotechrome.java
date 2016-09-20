package selenium.tests;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.experimental.categories.Category;

import java.net.MalformedURLException;
import java.net.URL;

public class remotechrome {

    @Test
    public void test() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        capabilities.setBrowserName("chrome");
        capabilities.setVersion("53");
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability("applicationName", "PCWIN10_64bit");

        RemoteWebDriver driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.google.com/");
        Assert.assertEquals("Title check failed!", "Google", driver.getTitle());
//commentary
        //Step 4- Close Driverremote
        driver.close();

        //Step 5- Quit Driver
        driver.quit();
    }
}