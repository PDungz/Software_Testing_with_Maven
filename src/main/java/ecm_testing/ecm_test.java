package ecm_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ecm_test {
	private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Set up WebDriver (Update the path to chromedriver according to your machine)
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the browser and navigate to the login page
        driver.get("http://127.0.0.1:8000/login");
        driver.manage().window().maximize();
    }

    @Test
    public void testLogin() {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        emailInput.sendKeys("phungvandung03062003@gmail.com");
        passwordInput.sendKeys("12345678");
        loginButton.click();        
    }

    @AfterClass
    public void tearDown() {
//        driver.quit();
    }
}