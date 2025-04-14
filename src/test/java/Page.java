import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public final class Page {
    public static final String URL = "http://localhost:8081/";
    private static final Logger logger = LoggerFactory.getLogger(Page.class);
    
    static final By PHONES_PAGE_LINK = By.xpath("//*[@id=\"narbar-menu\"]/ul/li[6]/a");
    static final By CAMERAS_PAGE_LINK = By.xpath("//*[@id=\"narbar-menu\"]/ul/li[7]/a");
    static final By TABLETS_PAGE_LINK = By.xpath("//*[@id=\"narbar-menu\"]/ul/li[4]/a");
    static final By WISHLIST_ON_PRODUCT_PAGE_BUTTON = By.xpath("//*[@id=\"content\"]/div[1]/div[2]/form/div/button[1]");
    static final By PRODUCT_NAME = By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/a/img");
    static final By OPTIONAL_SUBMENU = By.xpath("//*[@id=\"input-option-226\"]");
    static final By RED_COLOR = By.xpath("//*[@id=\"input-option-226\"]/option[2]");
    static final By ADD_TO_CART_BUTTON = By.xpath("//*[@id=\"button-cart\"]");
    static final By REVIEWS_BUTTON = By.xpath("//*[@id=\"content\"]/ul/li[3]/a");
    static final By CONTINUE_REVIEW_BUTTON = By.xpath("//*[@id=\"button-review\"]");
    static final By REVIEW_NAME_FIELD = By.xpath("//*[@id=\"input-name\"]");
    static final By REVIEW_TEXT = By.xpath("//*[@id=\"input-text\"]");
    static final By REVIEW_RATING = By.xpath("//*[@id=\"input-rating\"]/input");
    static final By LOGIN_ALERT = By.xpath("//*[@id=\"alert\"]/div");
    static final By CART_ALERT = By.xpath("//*[@id=\"alert\"]/div");
    
    private final WebDriver driver;
	
	public Page(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        logMe();
    }
    
    public void openMainPage() {
        driver.get(URL);
        logger.info("Page opened");
    }
    
    private String methodName(int depth) {
        return Thread.currentThread().getStackTrace()[depth].getMethodName();
    }
    private String methodName() {
        return methodName(3);
    }
    private void logMe() {
        logger.info("Call {}", methodName(3));
    }
    
    @Step("click product name")
    public void clickProductName(int productIndex, By frameLocator, int maxAttempts) {
        logMe();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                switchToFrameIfPresent(frameLocator);

                WebElement product = findProductByIndex(productIndex);
                scrollIntoViewAndClick(product);

                return;
            } catch (TimeoutException | StaleElementReferenceException | InterruptedException e) {
                printError("Ошибка (попытка " + (attempt + 1) + "): " + e.getMessage());
            } finally {
                switchToDefaultContent();
            }
        }
        printError("Не удалось кликнуть на элемент с индексом " + productIndex + " после " + maxAttempts + " попыток");
    }

    @Step("find and click element")
    public void findAndClickElement(By locator, By frameLocator, int maxAttempts) {
        logMe();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                switchToFrameIfPresent(frameLocator);

                Thread.sleep(1000);

                WebElement element = waitForElement(locator, 10);
                scrollIntoViewAndClick(element);

                return;
            } catch (TimeoutException | StaleElementReferenceException | InterruptedException e) {
                printError("Ошибка (попытка " + (attempt + 1) + "): " + e.getMessage());
            } finally {
                if (frameLocator != null) {
                    switchToDefaultContent();
                }
            }
        }
        printError("Не удалось кликнуть на элемент после " + maxAttempts + " попыток с локатором " + locator);
    }

    private void switchToFrameIfPresent(By frameLocator) {
        logMe();

        if (frameLocator != null) {
            try {
                WebElement frame = waitForElement(frameLocator, 3);
                driver.switchTo().frame(frame);
            } catch (TimeoutException | NoSuchFrameException e) {
                printError("Ошибка при переключении на фрейм " + frameLocator + ": " + e.getMessage());
            }
        }
    }

    private void switchToDefaultContent() {
        logMe();

        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            printError("Не удалось вернуться к основному контенту: " + e.getMessage());
        }
    }

    private WebElement findProductByIndex(int productIndex) {
        logger.info("Call {}({})", methodName(), productIndex);

        List<WebElement> products = waitForElements(PRODUCT_NAME, 10);
        return products.get(productIndex);
    }

    @Step("scroll into view and click")
    private void scrollIntoViewAndClick(WebElement element) throws InterruptedException {
        logMe();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        waitForElementToBeClickable(element, 10);

        try {
            Thread.sleep(1000);
            element.click();
        } catch (StaleElementReferenceException e) {
            printError("StaleElementReferenceException при клике: " + e.getMessage());
            throw e;
        }
    }

    private WebElement waitForElement(By locator, int timeout) {

        logMe();

        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private List<WebElement> waitForElements(By locator, int timeout) {
        logMe();

        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    private void waitForElementToBeClickable(WebElement element, int timeout) {
        logMe();

        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    private void printError(String message) {
        System.out.println(message);
    }

    @Step("Open phones page")
    public void openPhonesPage() {
        driver.findElement(PHONES_PAGE_LINK).click();
        
        logMe();
    }

    @Step("Open cameras page")
    public void openCamerasPage() {
        driver.findElement(CAMERAS_PAGE_LINK).click();

        
        logMe();
    }

    @Step("Open optional options")
    public void openOptionalOptions() throws InterruptedException {
        WebElement element = waitForElement(OPTIONAL_SUBMENU, 10);
        scrollIntoViewAndClick(element);
        driver.findElement(OPTIONAL_SUBMENU).click();

        
        logMe();
    }

    @Step("Open tablets page")
    public void openTabletsPage() {
        driver.findElement(TABLETS_PAGE_LINK).click();

        
        logMe();
    }

    @Step("select red color")
    public void selectRedColor() {
        driver.findElement(RED_COLOR).click();
        
        logMe();
    }

    public boolean isLoginAlertDisplayed() {

        
        logMe();

        try {
            return driver.findElement(LOGIN_ALERT).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCartAlertDisplayed() {
        logMe();

        try {
            return driver.findElement(CART_ALERT).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isThisCartAlert(String expected) {
        logger.info("Call {}({})", methodName(), expected);

        String actual = driver.findElement(CART_ALERT).getAttribute("textContent");
        if (actual.equals(expected)) {
            return true;
        } else {
            printError("Текст уведомления не совпадает");
            return false;
        }
    }

    @Step("write a review")
    public void writeAReview(String name, String reviewText) {
        logger.info("Call {}({}, {})", methodName(), name, reviewText);

        findAndClickElement(REVIEW_NAME_FIELD, null, 3);
        WebElement nameField = driver.findElement(REVIEW_NAME_FIELD);
        nameField.clear();
        nameField.sendKeys(name);
        findAndClickElement(REVIEW_TEXT, null, 3);
        WebElement textField = driver.findElement(REVIEW_TEXT);
        textField.clear();
        textField.sendKeys(reviewText);
    }

    @Step("rate the product")
    public void rateTheProduct(int rating) throws InterruptedException {
        logger.info("Call {}({})", methodName(), rating);

        List<WebElement> buttons = driver.findElements(REVIEW_RATING);
        for (WebElement button : buttons) {
            if (button.getAttribute("value").equals(String.valueOf(rating))) {
                button.click();
                return;
            }
        }
        printError("Кнопка с рейтингом " + rating + " не найдена");
    }

    public String getActualReviewerName() {
        logMe();
        return driver.findElement(REVIEW_NAME_FIELD).getAttribute("value");
    }

    public String getActualReviewText() {
        logMe();
        return driver.findElement(REVIEW_TEXT).getAttribute("value");
    }
}