import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.List;


public class TestsKT2 {
    WebDriver driver = new FirefoxDriver();
    String URL = "http://localhost:8081/";

   
    @Test
    public void test1() throws InterruptedException {
        driver.get(URL);
	
       
        var scroll_arrow = driver.findElement(By.className("carousel-control-next"));
        scroll_arrow.click();
		
       	
        Thread.sleep(Duration.ofSeconds(2).toMillis());
		
       	
        scroll_arrow = driver.findElement(By.className("carousel-control-prev"));
        scroll_arrow.click();
		
        Thread.sleep(Duration.ofSeconds(2).toMillis());
		
       	
        var mainProduct = driver.findElement(By.id("carousel-banner-0"));
        mainProduct.click();
		
        driver.quit();
    }

	
	
    private List<WebElement> getCurrencyItems() {
        WebElement dropdown_toggle = driver.findElement(By.className("dropdown-toggle"));
        dropdown_toggle.click();
        WebElement dropdown_menu = driver.findElement(By.className("dropdown-menu"));
        return dropdown_menu.findElements(By.className("dropdown-item"));
    }

   
    @Test
    public void test2() throws InterruptedException {
        driver.get(URL);

        WebElement currency_item = getCurrencyItems().getFirst();
        currency_item.click();

        Thread.sleep(Duration.ofSeconds(2).toMillis());

        currency_item = getCurrencyItems().getLast();
        currency_item.click();

        driver.quit();
    }

   
    @Test
    public void test3() throws InterruptedException {
        driver.get(URL);
        
        List<WebElement> nav_item_dropdown = driver.findElements(By.className("nav-item"));
        nav_item_dropdown.getFirst().click();
        
        WebElement nav_link = driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/div/div/ul/li[1]/a"));
        nav_link.click();

        Thread.sleep(Duration.ofSeconds(2).toMillis());

        driver.quit();
    }

   
    @Test
    public void test4() throws InterruptedException {
        driver.get(URL + "en-gb?route=account/register");
        
        WebElement first_name_field = driver.findElement(By.id("input-firstname"));
        WebElement last_name_field = driver.findElement(By.id("input-lastname"));
        WebElement email_field = driver.findElement(By.id("input-email"));
        WebElement password_field = driver.findElement(By.id("input-password"));
        WebElement form_check_input_field = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/div/input"));
        WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/button"));
        
        first_name_field.sendKeys("John");
        last_name_field.sendKeys("Doe");
        email_field.sendKeys("john.doe@gmail.com");
        password_field.sendKeys("password");
       
        form_check_input_field.click();
       
        submit_button.click();

        driver.quit();
    }

   
    @Test
    public void test5() throws InterruptedException {
        driver.get(URL);
        
        WebElement search_field = driver.findElement(By.xpath("//*[@id=\"search\"]/input"));
        WebElement search_button = driver.findElement(By.xpath("//*[@id=\"search\"]/button"));
       
        search_field.sendKeys("test");
        search_button.click();

        driver.quit();
    }
}