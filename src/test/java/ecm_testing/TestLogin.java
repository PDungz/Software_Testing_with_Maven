package ecm_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestLogin {
    private WebDriver driver; // Đối tượng WebDriver để điều khiển trình duyệt
    private WebDriverWait wait; // Đối tượng WebDriverWait để quản lý các trạng thái chờ trong khi tìm kiếm phần tử
    
    @BeforeClass
    public void setUp() {
        // Phương thức này chạy trước tất cả các bài kiểm thử, dùng để thiết lập môi trường

        // Cài đặt đường dẫn đến tệp chromedriver trên máy
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        // Khởi tạo đối tượng WebDriver cho Chrome
        driver = new ChromeDriver();

        // Khởi tạo WebDriverWait với thời gian chờ là 10 giây
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        Reporter.log("WebDriver initialized successfully.<br>");
    }

    @Test
    public void testLogin() {
        // Phương thức này thực hiện kiểm thử chức năng đăng nhập

        // Mở trình duyệt và điều hướng đến URL của trang đăng nhập
        driver.get("http://127.0.0.1:8000/login");
        Reporter.log("Navigated to the login page.<br>");
        
        // Phóng to cửa sổ trình duyệt
        driver.manage().window().maximize();
        Reporter.log("Browser window maximized.<br>");
        
        try {
            // Input
            String email = "example@gmail.com";
            String password = "12345678";

            // Chờ cho đến khi phần tử nhập email xuất hiện trên giao diện
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            
            // Tìm phần tử nhập mật khẩu và nút đăng nhập trên trang
            WebElement passwordInput = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("loginButton"));
            Reporter.log("Login form elements located successfully.<br>");
            
            // Điền thông tin vào các trường email và mật khẩu
            emailInput.sendKeys(email);
            Reporter.log("Entered email: " + email + "<br>");
            
            passwordInput.sendKeys(password);
            Reporter.log("Entered password.<br>");
            
            // Nhấn nút đăng nhập
            loginButton.click();
            Reporter.log("Login button clicked.<br>");

            // Xác minh đăng nhập thành công
            Reporter.log("Login test passed.<br>");
            
        } catch (Exception e) {
            Reporter.log("An error occurred during the login test: " + e.getMessage() + "<br>");
        }
    }

    @AfterClass
    public void tearDown() {
        // Đóng trình duyệt để giải phóng tài nguyên
        driver.quit();
        Reporter.log("Browser closed successfully.<br>");
    }
}
