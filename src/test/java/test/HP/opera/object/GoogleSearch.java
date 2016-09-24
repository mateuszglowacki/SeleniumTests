package test.HP.opera.object;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleSearch extends Base {

    By searchBox = By.name("q");
    By searchBoxSubmit = By.xpath("//button[@name='btnG']");
    By topSearchResult = By.cssSelector("#rso .g");

    public GoogleSearch(WebDriver _driver) {
        super(_driver, "http://www.google.com");
        visit();
        verifyPage();
    }

    public void searchFor(String searchTerm) {
        clear(searchBox);
        type(searchBox, searchTerm);
        //clickOn(searchBoxSubmit);
    }

    public void searchResultPresent(String searchResult) {
        waitFor().until(displayed(topSearchResult));
        Assert.assertTrue(textOf(topSearchResult).contains(searchResult));
    }

    public void verifyPage() {
        Assert.assertTrue(title().contains("google"));
    }
}