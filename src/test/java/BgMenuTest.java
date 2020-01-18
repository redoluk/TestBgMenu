

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class BgMenuTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://bgmenu.com");
	}

	@AfterMethod
	public void quit() {
		driver.quit();
	}

	@Test
	public void searchRestaurant() {
		WebElement element = driver.findElement(By.xpath("//input[@name='mysearchstring']"));
		element.click();
		element.clear();
		element.sendKeys("ул. Кюстендил, София", Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver driver) {
                return driver.findElement(By.cssSelector("span.atom-dropdown-text")).getText().contains("ул. Кюстендил, София");
            }
        });

// the same thing can be done with JDK 8 lambda
//		new WebDriverWait(driver, 5)
//				.until((WebDriver d) -> d.findElement(By.cssSelector("span.atom-dropdown-text")).
//						getText().contains("ул. Кюстендил, София"));

		Actions click = new Actions(driver);
		click.doubleClick(driver.findElement(By.xpath("//span[@class='btn-text-placeholder btn-search-text-placeholder']")))
				.sendKeys("Kayo").click().perform();

        String allRestaurantsText = driver.findElement(By.cssSelector(".restaurants")).getText();

		assertTrue(allRestaurantsText.contains("Kayo Sushi"));
	}
}
