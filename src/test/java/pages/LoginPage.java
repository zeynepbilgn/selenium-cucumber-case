package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ReusableMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static base.BaseTest.driver;

public class LoginPage {
    private static final String SEPARATOR = ",";

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    static WebDriverWait wait = new WebDriverWait(driver, 10);
    private static final Logger log = Logger.getLogger(String.valueOf(ReusableMethods.class));


    @FindBy(xpath = "//p[text()='Giriş Yap']")
    public WebElement signUp;

    @FindBy(id = "login-email")
    public WebElement eMailField;

    @FindBy(id = "login-password-input")
    public WebElement passwordField;

    @FindBy(xpath = "//span[@class='message']")
    public WebElement errorMessage;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginButton;

    public void loginWithInvalidCredentials(String email, String password) {

        eMailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();

        Assert.assertTrue("Incorrect login warning is not displayed", wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed());

        log.info("Incorrect login attempted - User email: " + email + ", Password: " + password);
    }


    public void runLoginTestFromCSV(String csvFilePath) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {

                String[] credentials = line.split(SEPARATOR);

                loginWithInvalidCredentials(credentials[0], credentials[1]);
            }
        } catch (IOException e) {

            log.info("An error occurred while reading the CSV file.");
        }
    }

}