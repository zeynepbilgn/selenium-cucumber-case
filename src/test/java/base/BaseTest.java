package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.ConfigReader;
import util.ReusableMethods;

import java.util.Properties;

public class BaseTest {
    public static WebDriver driver;


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        ConfigReader.initialize_Properties();
        driver.get(ConfigReader.getProperties().getProperty("url"));
        driver.manage().window().maximize();
        ReusableMethods.deleteCsvContent("src/test/resources/csv/price.csv");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
