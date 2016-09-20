package selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.Assert;
import org.junit.Test;

public class remote {

    @Test
    public void test() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

        capabilities.setBrowserName("internet explorer");
        capabilities.setVersion("11");
        capabilities.setPlatform(Platform.WINDOWS);

        RemoteWebDriver driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.navigate().to("https://www.teknosa.com/");
        Assert.assertEquals("Title check failed!", "Teknosa Alışveriş Sitesi - Herkes İçin Teknoloji", driver.getTitle());

        //Step 4- Close Driverremote
        driver.close();

        //Step 5- Quit Driver
        driver.quit();
    }
}