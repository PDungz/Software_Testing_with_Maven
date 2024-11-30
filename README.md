# **Hướng dẫn sử dụng chung cho chương trình kiểm thử tự động hóa với Selenium và TestNG**

## Dữ liệu chuẩn bị và lưu ý

### **1. Xem file docx KTPM_ONTHI.docx**

- Đọc hướng dẫn trong file docx để hiểu cách sử dụng

### **2. Trang web sử dụng để test nằm trong dự án ecm laravel**

- **Tải về ở đây** [ECM Shopee Cart Auth Repository](https://github.com/PDungz/Laravel/tree/master/ecm-shopee-cart-auth)

---

Dưới đây là các bước hướng dẫn cơ bản để thiết lập và sử dụng chương trình kiểm thử tự động hóa với Selenium và TestNG:

---

## **1. Cài đặt môi trường**

1. **Cài đặt Java JDK**:
   - Tải và cài đặt [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Kiểm tra cài đặt bằng lệnh:
     ```bash
     java -version
     ```
2. **Cài đặt Maven**:
   - Tải và cài đặt [Apache Maven](https://maven.apache.org/download.cgi).
   - Kiểm tra cài đặt bằng lệnh:
     ```bash
     mvn -version
     ```
3. **Cài đặt Google Chrome và ChromeDriver - `Lưu ý về phiên bản google vì sau một khoảng thời gian sẽ tự động cập nhật sẽ không tương thích với chromeDrive cũ nữa`**:
   - Tải và cài đặt [Google Chrome](https://www.google.com/chrome/).
   - Tải [ChromeDriver](https://chromedriver.chromium.org/downloads) phù hợp với phiên bản Chrome của bạn.
   - Lưu ChromeDriver ở một vị trí dễ tìm trên máy tính (ví dụ: `C:\chromedriver-win64\chromedriver.exe`).

---

## **2. Tạo và thiết lập dự án Maven**

1.  **Tạo dự án Maven**:

    - Sử dụng lệnh dòng lệnh:
      ```bash
      mvn archetype:generate -DgroupId=selenium.example -DartifactId=selenium-tests -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
      ```
    - Hoặc tạo một dự án Maven bằng IDE như IntelliJ IDEA hoặc Eclipse.

2.  **Cấu hình file `pom.xml`**:

    - Mở file `pom.xml`:

      ```xml
         <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
         <!-- Xác định phiên bản mô hình POM (Project Object Model) -->
         <modelVersion>4.0.0</modelVersion>
         <groupId>ecm_testing</groupId> <!-- Tên nhóm dự án -->
         <artifactId>ecm_testing</artifactId> <!-- Tên dự án -->
         <version>0.0.1-SNAPSHOT</version> <!-- Phiên bản của dự án (SNAPSHOT cho bản phát triển) -->

         <!-- Cấu hình mã hóa của dự án -->
         <properties>
             <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding> <!-- Mã hóa nguồn -->
             <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding> <!-- Mã hóa đầu ra báo cáo -->
         </properties>

        <!-- Danh sách các phụ thuộc cần thiết để chạy hoặc xây dựng dự án -->
        <dependencies>

        </dependencies>

        <!-- Cấu hình quy trình build của Maven -->
        <build>
            <plugins>
                <!-- Plugin Maven Compiler để biên dịch mã nguồn Java -->

                <!-- Plugin Maven Surefire để chạy các test case với TestNG -->
            </plugins>
        </build>
        </project>
      ```

    - Mở file `pom.xml` và thêm các thư viện phụ thuộc:

      ```xml
      <!-- Danh sách các phụ thuộc cần thiết để chạy hoặc xây dựng dự án -->
      <dependencies>
          <!-- Thư viện TestNG để viết và chạy test cases -->
          <dependency>
              <groupId>org.testng</groupId>
              <artifactId>testng</artifactId>
              <version>7.7.0</version>
              <scope>test</scope> <!-- Chỉ sử dụng trong giai đoạn test -->
          </dependency>

          <!-- Thư viện Selenium Java để tự động hóa trình duyệt -->
          <dependency>
              <groupId>org.seleniumhq.selenium</groupId>
              <artifactId>selenium-java</artifactId>
              <version>4.20.0</version>
          </dependency>

          <!-- WebDriverManager để tự động quản lý driver trình duyệt -->
          <dependency>
              <groupId>io.github.bonigarcia</groupId>
              <artifactId>webdrivermanager</artifactId>
              <version>5.5.0</version>
          </dependency>

          <!-- Thư viện SLF4J Simple để log đơn giản -->
          <dependency>
              <groupId>org.slf4j</groupId>
              <artifactId>slf4j-simple</artifactId>
              <version>1.7.36</version>
          </dependency>
      </dependencies>
      ```

3.  **Cấu hình plugin Maven để chạy TestNG**:

    ```xml
     <!-- Cấu hình quy trình build của Maven -->
     <build>
         <plugins>
             <!-- Plugin Maven Compiler để biên dịch mã nguồn Java -->
             <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-compiler-plugin</artifactId>
                 <version>3.8.1</version>
                 <configuration>
                     <source>17</source> <!-- Phiên bản mã nguồn Java (Java 17) -->
                     <target>17</target> <!-- Phiên bản mục tiêu biên dịch (Java 17) -->
                 </configuration>
             </plugin>

             <!-- Plugin Maven Surefire để chạy các test case với TestNG -->
             <plugin>
                 <groupId>org.apache.maven.plugins</groupId>
                 <artifactId>maven-surefire-plugin</artifactId>
                 <version>3.0.0-M5</version>
                 <configuration>
                     <!-- Tập tin cấu hình TestNG (testng.xml) -->
                     <suiteXmlFiles>
                         <suiteXmlFile>testng.xml</suiteXmlFile>
                     </suiteXmlFiles>
                 </configuration>
             </plugin>
         </plugins>
     </build>
    ```

---

## **3. Viết code kiểm thử**

### **3.1. Tạo class kiểm thử**:

- Tạo một file Java mới (ví dụ: `TestLogin.java`) trong thư mục `src/test/java`.

### **3.2. Viết code kiểm thử**:

- Dưới đây là ví dụ chung:

  ```java
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
          public void testLogin(ITestContext context) {
              // Phương thức này thực hiện kiểm thử chức năng đăng nhập

              // Mở trình duyệt và điều hướng đến URL của trang đăng nhập
              driver.get("http://127.0.0.1:8000/login");
              Reporter.log("Navigated to the login page.<br>");

              // Phóng to cửa sổ trình duyệt
              driver.manage().window().maximize();
              Reporter.log("Browser window maximized.<br>");

              try {
                  // Input
                  String email = "phungvandung03062003@gmail.com";
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
  ```

### **3.3. Các câu lệnh sử dụng trong với driver**:

Trong Selenium WebDriver, đối tượng `driver` cung cấp nhiều phương thức để tương tác với trình duyệt web. Dưới đây là danh sách các phương thức phổ biến và cách sử dụng chúng:

---

#### **3.3.1. Điều Hướng (Navigation)**

- `driver.get(String url)`
- **Mô tả**: Mở trang web bằng URL được cung cấp.
- **Cách dùng**:

  ```java
  driver.get("https://example.com");
  ```

- `driver.navigate().to(String url)`
- **Mô tả**: Chuyển hướng đến URL, tương tự `get()`.
- **Cách dùng**:

  ```java
  driver.navigate().to("https://example.com");
  ```

- `driver.navigate().back()`
- **Mô tả**: Quay lại trang trước đó trong lịch sử trình duyệt.
- **Cách dùng**:

  ```java
  driver.navigate().back();
  ```

- `driver.navigate().forward()`

- **Mô tả**: Đi tới trang tiếp theo trong lịch sử trình duyệt (nếu có).
- **Cách dùng**:

  ```java
  driver.navigate().forward();
  ```

- `driver.navigate().refresh()`

- **Mô tả**: Làm mới trang hiện tại.
- **Cách dùng**:
  ```java
  driver.navigate().refresh();
  ```

---

#### **3.3.2. Điều Khiển Cửa Sổ Trình Duyệt**

- `driver.manage().window().maximize()`

- **Mô tả**: Phóng to cửa sổ trình duyệt.
- **Cách dùng**:

  ```java
  driver.manage().window().maximize();
  ```

- `driver.manage().window().minimize()`

- **Mô tả**: Thu nhỏ cửa sổ trình duyệt.
- **Cách dùng**:

  ```java
  driver.manage().window().minimize();
  ```

- `driver.manage().window().fullscreen()`

- **Mô tả**: Hiển thị trình duyệt ở chế độ toàn màn hình.
- **Cách dùng**:

  ```java
  driver.manage().window().fullscreen();
  ```

- `driver.manage().window().setSize(Dimension size)`

- **Mô tả**: Thiết lập kích thước cụ thể cho cửa sổ trình duyệt.
- **Cách dùng**:
  ```java
  driver.manage().window().setSize(new Dimension(1024, 768));
  ```

---

#### **3.3.3. Tìm Phần Tử Web**

- `driver.findElement(By locator)`

- **Mô tả**: Tìm một phần tử duy nhất trên trang web.
- **Cách dùng**:

  ```java
  WebElement element = driver.findElement(By.id("email"));
  ```

- `driver.findElements(By locator)`

- **Mô tả**: Tìm tất cả các phần tử phù hợp với bộ định vị, trả về danh sách.
- **Cách dùng**:

  ```java
  List<WebElement> elements = driver.findElements(By.className("button-class"));
  ```

  **Tìm hiểu thêm về**: [Cách sử dụng `driver.findElement`](#cách-sử-dụng-driverfindelement)

---

#### **3.3.4. Quản Lý Cookie**

- `driver.manage().getCookies()`

- **Mô tả**: Trả về tất cả các cookie hiện tại trong trình duyệt.
- **Cách dùng**:

  ```java
  Set<Cookie> cookies = driver.manage().getCookies();
  ```

- `driver.manage().addCookie(Cookie cookie)`

- **Mô tả**: Thêm cookie mới vào trình duyệt.
- **Cách dùng**:

  ```java
  Cookie cookie = new Cookie("test", "value");
  driver.manage().addCookie(cookie);
  ```

- `driver.manage().deleteCookieNamed(String name)`

- **Mô tả**: Xóa cookie bằng tên.
- **Cách dùng**:
  ```java
  driver.manage().deleteCookieNamed("test");
  ```

---

#### **3.3.5. Quản Lý Thời Gian Chờ**

- `driver.manage().timeouts().implicitlyWait(Duration duration)`

- **Mô tả**: Thiết lập thời gian chờ ngầm định khi tìm kiếm phần tử.
- **Cách dùng**:

  ```java
  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  ```

- `driver.manage().timeouts().pageLoadTimeout(Duration duration)`

- **Mô tả**: Thiết lập thời gian chờ tải trang.
- **Cách dùng**:

  ```java
  driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
  ```

- `driver.manage().timeouts().scriptTimeout(Duration duration)`

- **Mô tả**: Thiết lập thời gian chờ cho các script JavaScript.
- **Cách dùng**:
  ```java
  driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
  ```

---

#### **3.3.6. Lấy Thông Tin Trang Web**

- `driver.getTitle()`

- **Mô tả**: Trả về tiêu đề của trang web.
- **Cách dùng**:

  ```java
  String title = driver.getTitle();
  ```

- `driver.getCurrentUrl()`

- **Mô tả**: Trả về URL hiện tại của trang web.
- **Cách dùng**:

  ```java
  String currentUrl = driver.getCurrentUrl();
  ```

- `driver.getPageSource()`

- **Mô tả**: Trả về mã nguồn HTML của trang web.
- **Cách dùng**:
  ```java
  String pageSource = driver.getPageSource();
  ```

---

#### **3.3.7. Đóng Trình Duyệt**

- `driver.close()`

- **Mô tả**: Đóng cửa sổ trình duyệt hiện tại.
- **Cách dùng**:

  ```java
    driver.close();
  ```

- `driver.quit()`

- **Mô tả**: Đóng tất cả các cửa sổ trình duyệt đang mở và thoát khỏi WebDriver.
- **Cách dùng**:
  ```java
    driver.quit();
  ```

---

### **Ví Dụ Tổng Hợp**

```java
    driver.get("https://example.com");
    System.out.println("Title: " + driver.getTitle());
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.findElement(By.id("searchInput")).sendKeys("Selenium WebDriver");
    driver.findElement(By.id("searchButton")).click();
    System.out.println("Current URL: " + driver.getCurrentUrl());
    driver.quit();
```

---

## **4. Chạy chương trình kiểm thử**

1. **Tạo file `testng.xml`**:

- Tạo file `testng.xml` tại thư mục gốc của dự án:
  ```xml
  <!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
  <suite name="Suite">
      <test name="TestLogin">
          <classes>
              <class name="ecm_testing.TestLogin"/>
          </classes>
      </test>
  </suite>
  ```

2. **Chạy kiểm thử**:

- Chạy lệnh:
  ```bash
  mvn test
  ```

---

## **5. Kiểm tra kết quả**

- Kết quả sẽ hiển thị trong **console** hoặc file báo cáo `target/surefire-reports`.

---

## **6. Debug lỗi (nếu cần)**

- **Lỗi không tìm thấy phần tử**: Xác minh ID, tên, hoặc class trong file HTML của trang web.
- **Sai URL**: Đảm bảo URL trong code đúng với ứng dụng của bạn.
- **Lỗi `chromedriver`**: Kiểm tra phiên bản ChromeDriver phù hợp với trình duyệt của bạn.

---

## **7. Tự động hóa nhiều kịch bản (tùy chọn)**

- **Sử dụng DataProvider**: Để kiểm thử nhiều bộ dữ liệu.
- **Thêm log**: Sử dụng `Logger` hoặc framework như `SLF4J` để ghi log chi tiết.

---

## **8. Tắt trình duyệt**

- Trình duyệt sẽ tự động đóng lại nếu bạn sử dụng `@AfterClass` với lệnh:

```java
driver.quit();
```

---

Chúc bạn thành công! 🚀🟢♻️

# **Mở rộng**

## **Cách sử dụng `driver.findElement`.**

Trong phương thức `driver.findElement(By. ...)`, chúng ta sử dụng lớp `By` để xác định cách thức tìm kiếm phần tử HTML trên trang web. Dưới đây là giải thích về các thành phần trong cú pháp này:

---

### **Cấu trúc tổng quát**

```java
driver.findElement(By.locator("value"));
```

- **`driver`**: Là đối tượng WebDriver dùng để điều khiển trình duyệt.
- **`findElement`**: Là phương thức của WebDriver dùng để tìm một **phần tử HTML duy nhất** trên trang (nếu có nhiều phần tử trùng khớp, nó sẽ chọn phần tử đầu tiên).
- **`By`**: Là một lớp cung cấp các phương thức **định vị phần tử**.
- **`locator`**: Phương pháp được sử dụng để tìm phần tử.
- **`value`**: Giá trị của locator, thường là một chuỗi.

---

### **Các loại locator phổ biến trong lớp `By`**

1. **`By.id(String id)`**

   - Sử dụng **thuộc tính `id`** của phần tử HTML để tìm kiếm.
   - Ví dụ:
     ```java
     driver.findElement(By.id("username"));
     ```
   - Phù hợp khi phần tử có `id` là **duy nhất** trên trang.

2. **`By.name(String name)`**

   - Sử dụng **thuộc tính `name`** của phần tử HTML.
   - Ví dụ:
     ```java
     driver.findElement(By.name("email"));
     ```
   - Thường dùng cho các trường nhập liệu (`<input>`).

3. **`By.className(String className)`**

   - Sử dụng **thuộc tính `class`** của phần tử HTML.
   - Ví dụ:
     ```java
     driver.findElement(By.className("login-button"));
     ```
   - Lưu ý: Nếu có nhiều phần tử cùng class, nó sẽ chọn phần tử đầu tiên.

4. **`By.tagName(String tagName)`**

   - Sử dụng **tên thẻ HTML** để tìm kiếm (ví dụ: `<div>`, `<input>`, `<button>`).
   - Ví dụ:
     ```java
     driver.findElement(By.tagName("button"));
     ```
   - Thường dùng khi muốn chọn phần tử theo thẻ HTML.

5. **`By.linkText(String text)`**

   - Sử dụng **văn bản hiển thị** của thẻ liên kết (`<a>`).
   - Ví dụ:
     ```java
     driver.findElement(By.linkText("Click here"));
     ```
   - Dùng khi bạn muốn tìm một liên kết có nội dung cụ thể.

6. **`By.partialLinkText(String text)`**

   - Sử dụng **một phần văn bản** của thẻ `<a>`.
   - Ví dụ:
     ```java
     driver.findElement(By.partialLinkText("Click"));
     ```
   - Thích hợp khi bạn chỉ nhớ một phần nội dung của liên kết.

7. **`By.cssSelector(String cssSelector)`**

   - Sử dụng **CSS Selector** để tìm kiếm phần tử.
   - Ví dụ:
     ```java
     driver.findElement(By.cssSelector("#loginForm > button"));
     ```
   - Là một phương pháp mạnh mẽ, hỗ trợ tìm kiếm theo cấu trúc phức tạp của trang.

8. **`By.xpath(String xpathExpression)`**
   - Sử dụng **XPath** để tìm kiếm phần tử.
   - Ví dụ:
     ```java
     driver.findElement(By.xpath("//input[@id='username']"));
     ```
   - Phù hợp khi cần tìm kiếm dựa trên **cấu trúc cây DOM** hoặc **thuộc tính động**.

---

### **Lưu ý khi sử dụng**

1. **Ưu tiên các locator đơn giản và duy nhất**:
   - Sử dụng `By.id` hoặc `By.name` nếu có thể, vì chúng thường nhanh hơn và ít lỗi.
2. **Sử dụng `By.cssSelector` và `By.xpath` cho cấu trúc phức tạp**:
   - Khi không có `id` hoặc `name`, bạn có thể sử dụng các selector phức tạp để định vị phần tử.
3. **Xử lý ngoại lệ**:
   - Nếu phần tử không được tìm thấy, Selenium sẽ ném ngoại lệ `NoSuchElementException`.

---

### **Ví dụ minh họa**

#### HTML mẫu:

```html
<form id="loginForm">
  <input
    type="text"
    id="username"
    name="username"
    class="input-field"
    placeholder="Enter your username"
  />
  <input
    type="password"
    id="password"
    name="password"
    class="input-field"
    placeholder="Enter your password"
  />
  <button type="submit" class="login-button">Login</button>
</form>
```

#### Code tìm các phần tử:

```java
// Tìm phần tử bằng id
WebElement usernameInput = driver.findElement(By.id("username"));

// Tìm phần tử bằng name
WebElement passwordInput = driver.findElement(By.name("password"));

// Tìm phần tử bằng className
WebElement loginButton = driver.findElement(By.className("login-button"));

// Tìm phần tử bằng cssSelector
WebElement form = driver.findElement(By.cssSelector("#loginForm"));

// Tìm phần tử bằng xpath
WebElement usernameInputXPath = driver.findElement(By.xpath("//input[@id='username']"));
```

---

Hy vọng giải thích này giúp bạn hiểu rõ hơn về cách sử dụng `driver.findElement(By. ...)`. Nếu cần hỗ trợ thêm, hãy cho tôi biết! 🚀
