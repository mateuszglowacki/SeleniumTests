package test.HP.firefox;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpStatusCodesTest {

    RemoteWebDriver driver;
    BrowserMobProxy proxy;

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
        // start the proxy immediately
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        // create a proxy instance and configure Selenium to use it
        // create an instance of Selenium connected to the proxy
        // Initialize
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        capabilities.setBrowserName("firefox");
        capabilities.setVersion("44");
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability("applicationName", "PCWIN10_64bit");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capabilities.getBrowserName() + ", Version: " + capabilities.getVersion() + ", Platform: " + capabilities.getPlatform());
        // enable detailed HAR capture
        proxy.enableHarCaptureTypes(
                CaptureType.REQUEST_CONTENT,
                CaptureType.RESPONSE_CONTENT);
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
    public void ResourceNotFound() {
        proxy.newHar();
        driver.navigate().to("http://the-internet.herokuapp.com/status_codes/404");
        Har har = proxy.getHar();
        int response = har.getLog().getEntries().get(0).getResponse().getStatus();
        assertThat(response, is(404));
    }

}
