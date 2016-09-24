package test.HP.chrome;

import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.*;

public class TablesTest {

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
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("53");
        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setCapability("applicationName", "PCWIN10_64bit");
        driver = new RemoteWebDriver(new URL(
                "http://localhost:4444/wd/hub"), capabilities);
        driver.manage().window().maximize();
        System.out.println("Browser name: " + capabilities.getBrowserName() + ", Version: " + capabilities.getVersion() + ", Platform: " + capabilities.getPlatform());
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        if(driver!=null) {
            System.out.println("Closing Chrome browser");
            driver.close();
            driver.quit();
        }
        System.out.println("...");
    }

    @Test
    public void withoutHelpfulMarkupDuesAscending() throws Exception {
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort dues column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get values from dues column (w/o $)
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for (WebElement element : dues) {
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        // check that dues are in ascending order
        for (int counter = 0; counter < dueValues.size() - 1; counter++) {
            assertThat(dueValues.get(counter), is(lessThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }

    @Test
    public void withoutHelpfulMarkupDuesDescending() throws Exception {
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort dues column in descending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get values from dues column (w/o $) again
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for (WebElement element : dues) {
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        // assert dues are in descending order
        for (int counter = 0; counter < dueValues.size() - 1; counter++) {
            assertThat(dueValues.get(counter), is(greaterThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }

    @Test
    public void withoutHelpfulMarkupEmailAscending() throws Exception{
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort email column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(3)")).click();

        // get values from email column
        List<WebElement> emails = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(3)"));

        // assert emails are in ascending order
        for(int counter = 0; counter < emails.size() -1; counter++){
            assertThat(
                    emails.get(counter).getText().compareTo(emails.get(counter + 1).getText()),
                    is(lessThan(0)));
            // checking for a negative number for ascending order
            // would check for a positive number if descending
        }
    }

    @Test
    public void withHelpfulMarkup() throws Exception
    {
        driver.get("http://the-internet.herokuapp.com/tables");
        driver.findElement(By.cssSelector("#table2 thead .dues")).click();
        List<WebElement> dues = driver.findElements(By.cssSelector("#table2 tbody .dues"));
        List<Double> dueValues = new LinkedList<Double>();
        for(WebElement element : dues){
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        for(int counter = 0; counter < dueValues.size() - 1; counter++){
            assertThat(dueValues.get(counter), is(lessThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }
}