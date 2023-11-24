package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.ReusableMethods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class CartPage {
    private static final Logger log = Logger.getLogger(String.valueOf(ReusableMethods.class));

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".add-to-basket")
    public WebElement addToCartButton;

    @FindBy(xpath = "(//span[@class='prc-dsc'])[1]")
    public static WebElement productPrice;

    @FindBy(css = ".pb-basket-item-price")
    public WebElement cartPrice;

    @FindBy(css = ".goBasket")
    public WebElement myCart;

    @FindBy(css = ".tooltip-content > button")
    public WebElement vasItem;

    @FindBy(css = ".i-trash")
    public List<WebElement> deleteBasket;


    @FindBy(css = ".counter-content")
    public WebElement quantityInput;

    @FindBy(css = ".pb-header")
    public WebElement emptyBasketMessage;

    @FindBy(xpath = "(//button[@class='ty-numeric-counter-button'])")
    public WebElement activeIncreaseButton;

    @FindBy(xpath = "(//button[@class='ty-numeric-counter-button passive'])[2]")
    public WebElement pasiveIncreaseButton;


    public void writeWebElementTextToCSV(WebElement element, String csvPath) {
        try {
            String elementText = element.getText();

            Assert.assertTrue("Text taken from WebElement is empty.", !elementText.isEmpty());

            log.info("Text taken from WebElement: " + elementText);
            writeTextToCSV(elementText, csvPath);
        } catch (Exception e) {
            log.warning("Could not get text from WebElement or write to CSV file.");
            Assert.fail("Could not get text from WebElement or write to CSV file.");
        }
    }

    private void writeTextToCSV(String text, String csvFilePath) {
        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            writer.append(text);
            writer.append("\n");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            log.info("Text successfully written to CSV file: " + text);
        } catch (IOException e) {

            log.warning("Text could not be written to CSV file.");
            Assert.fail("Text could not be written to CSV file.");
        }
    }

}
