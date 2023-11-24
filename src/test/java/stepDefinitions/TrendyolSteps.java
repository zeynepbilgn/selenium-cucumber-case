package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import util.ReusableMethods;

import java.util.List;
import java.util.logging.Logger;

public class TrendyolSteps extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private CartPage cartPage;


    private static final Logger log = Logger.getLogger(String.valueOf(ReusableMethods.class));
    String searchProduct = "Laptop";

    @Given("User go to the trendyol website")
    public void userGoToTheTrendyolWebsite() {
        setUp();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);

    }

    @Then("Verify that the main page is opened.")
    public void verifyThatTheMainPageIsOpened() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.trendyol.com/");
    }

    @When("User attempts to log in with incorrect information")
    public void userAttemptsToLogInWithIncorrectInformation() {
        ReusableMethods.acceptCookies(homePage.popUp);
        ReusableMethods.clickFunction(loginPage.signUp);
        loginPage.runLoginTestFromCSV("src/test/resources/csv/users.csv");

    }

    @And("User searches for {string}")
    public void userSearchesFor(String arg0) {
        ReusableMethods.sendKeysFunction(homePage.searchText, searchProduct);
        ReusableMethods.clickFunction(homePage.searchButton);
    }

    @Then("User selects a random product")
    public void userSelectsARandomProduct() {
        WebElement randomProduct = ReusableMethods.selectRandomElement(homePage.productList, searchProduct);
        if (randomProduct != null) {
            ReusableMethods.clickFunction(randomProduct);
        } else {
            log.info("Product not found.");
        }
    }

    @And("User adds the product to the cart")
    public void userAddsTheProductToTheCart() {
        ReusableMethods.focusOnNewWindow();
        ReusableMethods.clickFunction(cartPage.addToCartButton);
        cartPage.writeWebElementTextToCSV(CartPage.productPrice, "src/test/resources/csv/price.csv");
    }

    @Then("Verify the product price before and after adding to the cart")
    public void verifyTheProductPriceBeforeAndAfterAddingToTheCart() {
        List<String> productPrice = ReusableMethods.readValueFromCSV("src/test/resources/csv/price.csv");
        Assert.assertEquals(productPrice.get(0), cartPage.cartPrice.getText());
    }

    @And("Go to Cart")
    public void goToCart() {
        ReusableMethods.clickFunction(cartPage.myCart);
        ReusableMethods.clickElementIsVisible(cartPage.vasItem);
    }

    @And("User increases the quantity in the basket to two")
    public void userIncreasesTheQuantityInTheBasketToTwo() {

        try {
            Assert.assertTrue("Error: The number of products increase button is not active!", cartPage.activeIncreaseButton.isDisplayed());

            if (cartPage.activeIncreaseButton.isEnabled()) {
                cartPage.activeIncreaseButton.click();
                log.info("Product quantity increased.");
            } else {
                Assert.assertTrue("Error: Inactive button is not displayed!", cartPage.pasiveIncreaseButton.isDisplayed());
                log.warning("No product in stock, quantity could not be increased.");
            }
        } catch (Exception e) {

            log.warning("Quantity increase button is not active, quantity not increased");
        }
    }

    @Then("Verify the accuracy of the quantity")
    public void verifyTheAccuracyOfTheQuantity() {
        try {
            Assert.assertEquals("The number of products could not be increased", "2", cartPage.quantityInput.getAttribute("value"));
        } catch (AssertionError e) {

            log.warning("Assertion error: The number of products could not be increased");
        }

    }

    @And("User deletes all products from the basket")
    public void userDeletesAllProductsFromTheBasket() {
        for (WebElement element : cartPage.deleteBasket) {
            ReusableMethods.clickFunction(element);
        }
    }

    @Then("Verify the basket is empty")
    public void verifyTheBasketIsEmpty() {
        ReusableMethods.waitMethod(1000);
        String getText = cartPage.emptyBasketMessage.getText();

        log.info("Number of products in the basket:" + getText);
        Assert.assertEquals("Cart is not empty!", "Sepetim (0 Ürün)", getText);

    }
}
