import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;

public class A01_BookAddDelete {


    public static void main(String[]args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.amazon.com.tr");
        driver.findElement(By.xpath("//*[@id='sp-cc-rejectall-link']")).click();

        driver.findElement(By.xpath("//select[@id='searchDropdownBox']")).click();
        WebElement ddmList = driver.findElement(By.cssSelector("#searchDropdownBox"));
        Select select = new Select(ddmList);
        select.selectByVisibleText("Kitaplar");
        System.out.println(select.getFirstSelectedOption().getText());
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        searchBox.sendKeys("Umberto Eco"+ Keys.ENTER);

        driver.findElement(By.xpath("//img[@alt='Gülün Adı']")).click();

        WebElement sepeteEkle = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",sepeteEkle);
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();",sepeteEkle);

        driver.findElement(By.xpath("//a[@href='/cart?ref_=sw_gtc']")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("input[value='Sil']")).click();

        try {
            WebElement confirmationMessage = driver.findElement(By.cssSelector(".a-size-base.sc-list-item-removed-msg-text-delete"));
            String actualText = confirmationMessage.getText();
            String expectedText = "Gülün Adı Alışveriş Sepetiniz konumundan kaldırıldı.";

            if (actualText.contains(expectedText)) {
                System.out.println("Doğrulama başarılı: Beklenen mesaj göründü.");
            } else {
                System.out.println("Doğrulama başarısız: Beklenen mesaj görünmedi.");
            }
        } catch (Exception e) {
            System.out.println("Doğrulama başarısız: Mesaj bulunamadı.");
            e.printStackTrace();
        }

                driver.quit();

    }


}
