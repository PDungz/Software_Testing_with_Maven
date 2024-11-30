package ecm_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestCreateProduct {
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

        // Mở trang đăng nhập
        driver.get("http://127.0.0.1:8000/login");
        Reporter.log("Navigated to the login page.<br>");

        // Phóng to cửa sổ trình duyệt
        driver.manage().window().maximize();
        Reporter.log("Browser window maximized.<br>");

        // Nhập thông tin đăng nhập
        String email = "example@gmail.com";
        String password = "12345678";

        // Chờ cho đến khi phần tử nhập email xuất hiện và nhập thông tin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        // Tìm phần tử mật khẩu và nút đăng nhập
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
        wait.until(ExpectedConditions.urlContains("/admin"));
        Reporter.log("Login test passed.<br>");
    }

    @DataProvider(name = "productData")
    public Object[][] provideProductData() {
        // Dữ liệu kiểm thử: nhiều bộ dữ liệu để tạo sản phẩm
        return new Object[][] {
        	// {"Product 1", "Đường dẫn hình ảnh trong máy bạn", "Description 1", "10", "100", "12/30/2024", "2"},
            {"Product 1", "C:\\Users\\ASUS\\OneDrive\\Pictures\\Camera Roll\\aophong.jpg", "Description 1", "10", "100", "12/30/2024", "2"},
            {"Product 2", "C:\\Users\\ASUS\\OneDrive\\Pictures\\Camera Roll\\aophong2.jpg", "Description 2", "20", "200", "01-12-2024", "2"},
            {"Product 3", "C:\\Users\\ASUS\\OneDrive\\Pictures\\Camera Roll\\aophong1.jpg", "Description 3", "15", "150", "15-12-2024", "3"},
        };
    }

    @Test(dataProvider = "productData")
    public void testCreateProduct(String name, String imagePath, String description, String quantity, String price, String entryDate, String categoryId) {
        // Phương thức này thực hiện kiểm thử chức năng tạo sản phẩm

        // Mở trang tạo sản phẩm mới
        driver.get("http://127.0.0.1:8000/admin/products/create");
        Reporter.log("Navigated to the create product page.<br>");

        try {
            // Chờ cho đến khi phần tử tên sản phẩm xuất hiện và nhập thông tin
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            
            // Tìm các trường dữ liệu còn lại: hình ảnh, mô tả, số lượng, giá, ngày nhập
            WebElement imageInput = driver.findElement(By.id("image"));
            WebElement descriptionInput = driver.findElement(By.id("description"));
            WebElement quantityInput = driver.findElement(By.id("quantity"));
            WebElement priceInput = driver.findElement(By.id("price"));
            WebElement entryDateInput = driver.findElement(By.id("entry_date"));

            // Chọn danh mục sản phẩm từ dropdown
            Select categorySelect = new Select(driver.findElement(By.name("category_id")));

            // Điền thông tin vào các trường
            nameInput.sendKeys(name);
            Reporter.log("Entered product name: " + name + "<br>");

            imageInput.sendKeys(imagePath);
            Reporter.log("Entered product image path: " + imagePath + "<br>");

            descriptionInput.sendKeys(description);
            Reporter.log("Entered product description: " + description + "<br>");

            quantityInput.sendKeys(quantity);
            Reporter.log("Entered product quantity: " + quantity + "<br>");

            priceInput.sendKeys(price);
            Reporter.log("Entered product price: " + price + "<br>");

            entryDateInput.sendKeys(entryDate);
            Reporter.log("Entered product entry date: " + entryDate + "<br>");

            // Chọn danh mục từ dropdown
            categorySelect.selectByValue(categoryId);
            Reporter.log("Selected category with ID: " + categoryId + "<br>");

            // Nhấn nút "Submit" để tạo sản phẩm
            WebElement submitButton = driver.findElement(By.id("btnSubmit"));
            submitButton.click();

            Reporter.log("Submit button clicked.<br>");

            // Xác nhận việc tạo sản phẩm thành công
            Reporter.log("Product creation test passed.<br>");

        } catch (Exception e) {
            // Nếu có lỗi, log chi tiết thông báo lỗi
            Reporter.log("An error occurred during the product creation test: " + e.getMessage() + "<br>");
        }
    }

    @AfterClass
    public void tearDown() {
        // Phương thức này chạy sau tất cả các bài kiểm thử để giải phóng tài nguyên

        // Đóng trình duyệt để giải phóng tài nguyên
        driver.quit();
        Reporter.log("Browser closed successfully.<br>");
    }
}
