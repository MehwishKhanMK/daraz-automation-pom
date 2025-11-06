package mytestproject;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class DarazHomePage {
    WebDriver driver;
    WebDriverWait wait;

    public DarazHomePage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void openDaraz() {
        driver.get("https://www.daraz.pk/");
    }

    public void searchElectronics() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox.sendKeys("electronics");
            searchBox.submit();
        } catch (Exception e) {
            System.out.println("❌ Search box not found.");
        }
    }

    public void applyBrandFilter() throws InterruptedException {
        Thread.sleep(4000);
        try {
            // ✅ Scroll down to make brand filters visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(2000);

            // ✅ Target specific brand name
            WebElement brand = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[.//span[text()='Alsimba'] or .//span[text()='JoyClick'] or .//span[text()='Planet X']]")
            ));

            js.executeScript("arguments[0].scrollIntoView(true);", brand);
            brand.click();
            System.out.println("✅ Brand filter applied successfully (Joyroom/JoyClick/Infinix).");
        } catch (Exception e) {
            System.out.println("⚠ Brand filter not found or could not be applied.");
        }
    }

    public void applyPriceFilter() throws InterruptedException {
        Thread.sleep(4000);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(2000);

            // ✅ Find the Min and Max price inputs (Daraz updated them recently)
            WebElement minInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Min']")));
            WebElement maxInput = driver.findElement(By.xpath("//input[@placeholder='Max']"));
            WebElement applyBtn = driver.findElement(By.xpath("//button[contains(text(),'Apply') or @type='button']"));

            minInput.clear();
            minInput.sendKeys("500");
            maxInput.clear();
            maxInput.sendKeys("3000");
            applyBtn.click();

            System.out.println("✅ Price filter applied successfully (500 - 3000).");
        } catch (Exception e) {
            System.out.println("⚠ Price filter not found or could not be applied.");
        }
    }

    public int countProducts() throws InterruptedException {
        Thread.sleep(5000);
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@data-qa-locator,'product-item')]"));
        System.out.println("✅ Total products found: " + products.size());
        return products.size();
    }

    public void openFirstProduct() throws InterruptedException {
        Thread.sleep(4000);
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@data-qa-locator,'product-item')]"));
        if (!products.isEmpty()) {
            products.get(0).click();
            System.out.println("✅ Opened first product");
        } else {
            System.out.println("❌ No product to open");
        }
    }

    public boolean isFreeShippingAvailable() throws InterruptedException {
        Thread.sleep(5000);
        try {
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            WebElement freeShipping = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Free Shipping') or contains(text(),'free shipping')]")));
            System.out.println("🚚 Free Shipping is available!");
            return freeShipping.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ Free Shipping not available!");
            return false;
        }
    }

    public void closeBrowser() {
        driver.quit();
    }
}
