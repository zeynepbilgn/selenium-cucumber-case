package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "Rating-Review")
    public WebElement popUp;
    @FindBy(xpath = "//i[@data-testid='search-icon']")
    public WebElement searchButton;
    @FindBy(xpath = "//input[@data-testid='suggestion']")
    public WebElement searchText;

    @FindBy(xpath = "//div[@class='image-overlay-body']")
    public List<WebElement> productList;

}




