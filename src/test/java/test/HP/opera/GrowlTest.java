package test.HP.opera;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class GrowlTest {

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
    public void growlTest() throws Exception {
        driver.get("http://the-internet.herokuapp.com/");

        // Check for jQuery on the page, add it if need be
        js.executeScript("if (!window.jQuery) {" +
                "var jquery = document.createElement('script'); jquery.type = 'text/javascript';" +
                "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';" +
                "document.getElementsByTagName('head')[0].appendChild(jquery);" +
                "}");

        // Use jQuery to add jquery-growl to the page
        js.executeScript("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

        // Use jQuery to add jquery-growl styles to the page
        js.executeScript("$('head').append('<link rel=\"stylesheet\" " +
                "href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" " +
                "type=\"text/css\" />');");

        // jquery-growl w/ no frills
        js.executeScript("$.growl({ title: 'GET', message: '/' });");

        // jquery-growl w/ colorized output
        js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
        js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
        js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
        Thread.sleep(5000);
    }

}