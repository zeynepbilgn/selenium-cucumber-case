package util;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ReusableMethods extends BaseTest {
    static WebDriverWait wait = new WebDriverWait(driver, 10);
    private static final Logger log = Logger.getLogger(String.valueOf(ReusableMethods.class));

    public static void acceptCookies(WebElement acceptButton) {
        try {
            if (acceptButton.isDisplayed()) {
                acceptButton.click();
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("No Such Element !");
        }
    }


    public static void clickFunction(WebElement clickElement) {
        log.info("start element <click> process");
        wait.until(ExpectedConditions.elementToBeClickable(clickElement));
        clickElement.click();
        log.info("element <click> process finish successfully");
    }

    public static void sendKeysFunction(WebElement sendKeysElement, String value) {

        log.info("start element <send key> process");
        wait.until(ExpectedConditions.visibilityOf(sendKeysElement));
        sendKeysElement.sendKeys(value);
        log.info("element <send key> process finish successfully");

    }


    public static WebElement selectRandomElement(List<WebElement> elementList, String elementName) {

        if (elementList.isEmpty()) {
            log.info(elementName + " list is empty.");
            return null;
        }

        int randomIndex = new Random().nextInt(elementList.size());
        WebElement selectedElement = elementList.get(randomIndex);

        log.info("Random one " + elementName + " selected: " + selectedElement.getText());

        return selectedElement;

    }

    public static void focusOnNewWindow() {
        String oldWindow = driver.getWindowHandle();
        Set<String> windowSet = driver.getWindowHandles();
        windowSet.remove(oldWindow);
        String newWindow = windowSet.iterator().next();
        driver.switchTo().window(newWindow);
    }

    public static void clickElementIsVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException t) {
            log.info("element is not clickable then continue..");
        }
    }

    public static void deleteCsvContent(String csvPath) {
        try {
            File file = new File(csvPath);

            if (file.delete()) {
                log.info("CSV file successfully deleted");
            } else {
                log.info("CSV file could not delete");
            }
        } catch (Exception e) {
            throw new RuntimeException("error while deleting csv content");
        }
    }

    public static List<String> readValueFromCSV(String csvPath) {

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            List<String> dataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
            return dataList;

        } catch (IOException e) {
            throw new RuntimeException("error while reading csv file");
        }
    }

    public static void waitMethod(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (Exception ex) {
            throw new RuntimeException("error while wait");
        }
    }

}