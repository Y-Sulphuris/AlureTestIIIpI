import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPanel {
    protected static final By USERNAME_FIELD = By.xpath("//*[@id='input-username']");
    protected static final By PASSWORD_FIELD = By.xpath("//*[@id='input-password']");
    protected static final By LOGIN_BUTTON = By.xpath("//*[@id='form-login']/div[3]/button");
    protected static final String USERNAME = "user";
    protected static final String PASSWORD = "bitnami";
	
	protected static final By ADD_BUTTON = By.xpath("//*[@id=\"content\"]/div[1]/div/div/a");
	protected static final By DELETE_PRODUCT_BUTTON = By.xpath("//*[@id=\"content\"]/div[1]/div/div/button[3]");
	protected static final By SAVE_BUTTON = By.xpath("//*[@id=\"content\"]/div[1]/div/div/button");
    protected static final By CATALOG_MENU = By.xpath("//*[@id='menu-catalog']/a");
    protected static final By CATEGORIES_SUBMENU = By.xpath("//*[@id='collapse-1']/li[1]/a");
    protected static final By PRODUCTS_SUBMENU = By.xpath("//*[@id='collapse-1']/li[2]/a");
    protected static final By PRODUCT_NAME_FIELD = By.xpath("//*[@id='input-name-1']");
    protected static final By META_TAG_TITLE_FIELD = By.xpath("//*[@id='input-meta-title-1']");
    protected static final By CATEGORIES_FIELD = By.xpath("//*[@id='input-category']");
    protected static final By MODEL_FIELD = By.xpath("//*[@id='input-model']");
    protected static final By PRODUCT_SEO_TAB = By.xpath("//*[@id='form-product']//a[text()='SEO']");
    protected static final By CATEGORY_SEO_TAB = By.xpath("//*[@id='form-category']//a[text()='SEO']");
    protected static final By PRODUCT_DATA_TAB = By.xpath("//*[@id='form-product']//a[text()='Data']");
    protected static final By PRODUCT_LINKS_TAB = By.xpath("//*[@id='form-product']//a[text()='Links']");
    protected static final By SEO_URL_FIELD = By.xpath("//*[@id='input-keyword-0-1']");
    protected static final By MAIN_PAGE_LINK = By.xpath("//*[@id='header']/div/a/img");
    protected static final By CATEGORIES_TABLE_SECOND_PAGE = By.xpath("//*[@id='form-category']/div[2]/div[1]/ul/li[2]/a");
    protected static final By PRODUCTS_TABLE_SECOND_PAGE = By.xpath("//*[@id='form-product']/div[2]/div[1]/ul/li[2]/a");
    protected static final By DEVICES_CATEGORY = By.xpath("//*[@id='form-category']//table//tr[.//td[contains(text(), 'Devices')]]/td[2]");
    protected static final By DEVICES_DROPDOWN_MENU = By.xpath("//*[@id='autocomplete-category']/li/a");
    protected static final By SEARCH_FIELD = By.xpath("//input[@name='search']");
    protected static final By SEARCH_ICON = By.xpath("//i[@class='fa-solid fa-magnifying-glass']");
    protected static final By ALERT = By.xpath("//*[@id='alert']/div");
    protected static final By SEARCH_HEADER = By.xpath("//*[@id='content']/h1");
    protected static final By DELETE_CHECKBOX = By.xpath(".//input[@type='checkbox' and @name='selected[]']");
    protected static final By TABLE_PRODUCT_ROW = By.xpath("//table//tbody/tr");
    protected static final By TABLE_PRODUCT_NAME = By.xpath(".//td[3]");
	
	protected RemoteWebDriver driver;
	protected WebDriverWait wait;

    public AdminPanel(RemoteWebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    public void openAdminPage() {
        driver.get(Page.URL + "/administration");
    }

    public void findAndClickElement(By locator, By frameLocator, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                if (frameLocator != null) {
                    try {
                        WebElement frame = new WebDriverWait(driver, Duration.ofSeconds(3))
                                .until(ExpectedConditions.presenceOfElementLocated(frameLocator));
                        driver.switchTo().frame(frame);
                    } catch (TimeoutException | NoSuchFrameException e) {
                        System.out.println("Error switching to frame: " + e.getMessage());
                    }
                }

                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
                driver.executeScript("arguments[0].scrollIntoView();", element);
                new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(element));

                try {
                    driver.executeScript("arguments[0].click();", element);
                    return;
                } catch (StaleElementReferenceException e) {
                    System.out.println("StaleElementReferenceException on click (attempt " + (attempt + 1) + "): " + e.getMessage());
                }
            } catch (TimeoutException | StaleElementReferenceException e) {
                System.out.println("Error (attempt " + (attempt + 1) + "): " + e.getMessage());
            } finally {
                try {
                    if (frameLocator != null) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    System.out.println("Failed to return to default content: " + e.getMessage());
                }
            }
        }
        System.out.println("Мы не знаем что это такое " + maxAttempts + " если бы мы знали что это такое " + locator + " мы не знаем что это такое");
    }

    public void findAndClickElement(By locator) {
        findAndClickElement(locator, null, 3);
    }

    public WebElement findElement(By locator, By frameLocator, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                if (frameLocator != null) {
                    try {
                        WebElement frame = new WebDriverWait(driver, Duration.ofSeconds(10))
								.until(ExpectedConditions.presenceOfElementLocated(frameLocator));
                        driver.switchTo().frame(frame);
                    } catch (TimeoutException | NoSuchFrameException e) {
                        System.out.println("Error switching to frame: " + e.getMessage());
                    }
                }

                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
                driver.executeScript("arguments[0].scrollIntoView();", element);
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(element));
                return element;
            } catch (TimeoutException | StaleElementReferenceException e) {
                System.out.println("Error (attempt " + (attempt + 1) + "): " + e.getMessage());
            } finally {
                try {
                    if (frameLocator != null) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    System.out.println("Failed to return to default content: " + e.getMessage());
                }
            }
        }
        System.out.println("Failed to find element after " + maxAttempts + " attempts with locator " + locator);
        return null;
    }

    public WebElement findElement(By locator) {
        return findElement(locator, null, 3);
    }

    public void clickCatalog(By locator, By menuLocator, By frameLocator, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                if (frameLocator != null) {
                    try {
                        WebElement frame = new WebDriverWait(driver, Duration.ofSeconds(2))
                                .until(ExpectedConditions.presenceOfElementLocated(frameLocator));
                        driver.switchTo().frame(frame);
                    } catch (TimeoutException | NoSuchFrameException e) {
                        System.out.println("Error switching to frame: " + e.getMessage());
                    }
                }

                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
                driver.executeScript("arguments[0].scrollIntoView();", element);
                new WebDriverWait(driver, Duration.ofSeconds(1))
                        .until(ExpectedConditions.elementToBeClickable(element));

                try {
                    driver.executeScript("arguments[0].click();", element);
                    return;
                } catch (StaleElementReferenceException e) {
                    System.out.println("StaleElementReferenceException on click (attempt " + (attempt + 1) + "): " + e.getMessage());
                    reopenCatalog(menuLocator);
                    continue;
                }
            } catch (TimeoutException | StaleElementReferenceException e) {
                System.out.println("Error (attempt " + (attempt + 1) + "): " + e.getMessage());
                reopenCatalog(menuLocator);
                continue;
            } finally {
                try {
                    if (frameLocator != null) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    System.out.println("Failed to return to default content: " + e.getMessage());
                }
            }
        }
        System.out.println("Failed to click element after " + maxAttempts + " attempts with locator " + locator);
    }

    private void reopenCatalog(By catalogMenuLocator) {
        try {
            WebElement catalogMenu = new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(ExpectedConditions.elementToBeClickable(catalogMenuLocator));
            driver.executeScript("arguments[0].click();", catalogMenu);
        } catch (TimeoutException | StaleElementReferenceException e) {
        }
    }

    public void deleteProductsByName(List<String> productNames, By productRowLocator, By checkboxLocator,
                                     By deleteButtonLocator, By productNameLocator, By frameLocator) {
        if (frameLocator != null) {
            try {
                WebElement frame = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.presenceOfElementLocated(frameLocator));
                driver.switchTo().frame(frame);
            } catch (TimeoutException e) {
                System.out.println("Error switching to frame: " + e.getMessage());
                return;
            }
        }

        try {
            for (String productName : productNames) {
                try {
                    List<WebElement> productRows = new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(productRowLocator));
                    System.out.println("Found " + productRows.size() + " product rows.");

                    for (WebElement row : productRows) {
                        try {
                            WebElement nameElement = row.findElement(productNameLocator);
                            String fullProductName = nameElement.getText().trim();
                            String actualProductName = fullProductName.split("\n")[0].trim();

                            if (actualProductName.equals(productName)) {
                                System.out.println("Product '" + productName + "' found.");
                                try {
                                    WebElement checkbox = row.findElement(checkboxLocator);
                                    new WebDriverWait(driver, Duration.ofSeconds(15))
                                            .until(ExpectedConditions.elementToBeClickable(checkbox));
                                    checkbox.click();
                                    System.out.println("Clicked checkbox for product '" + productName + "'");
                                    break;
                                } catch (NoSuchElementException | TimeoutException e) {
                                    System.out.println("Failed to find or click checkbox for product '" + productName + "': " + e.getMessage());
                                    continue;
                                }
                            }
                        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
                            System.out.println("Error processing product row '" + productName + "': " + e.getMessage());
                            continue;
                        }
                    }
                } catch (TimeoutException e) {
                    System.out.println("Failed to find product rows!");
                    continue;
                }
            }

            try {
                WebElement deleteButton = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.elementToBeClickable(deleteButtonLocator));
                deleteButton.click();
            } catch (TimeoutException e) {
                System.out.println("Failed to find or click delete button: " + deleteButtonLocator);
                return;
            }

            try {
                Alert alert = new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.alertIsPresent());
                alert.accept();
            } catch (TimeoutException e) {
                System.out.println("Alert didn't appear!");
            } catch (Exception e) {
                System.out.println("Error handling alert: " + e.getMessage());
            }
        } catch (TimeoutException e) {
            System.out.println("Failed to find product rows or delete button!");
        }
        finally {
            if (frameLocator != null) {
                driver.switchTo().defaultContent();
            }
        }
    }

    public void login() {
        findElement(USERNAME_FIELD).sendKeys(USERNAME);
        findElement(PASSWORD_FIELD).sendKeys(PASSWORD);
        findAndClickElement(LOGIN_BUTTON);
    }

    public void openStartPage() {
        findAndClickElement(MAIN_PAGE_LINK);
    }

    public void openMainPage() {
        driver.get(Page.URL);
    }

    public void openCatalog(By submenuLocator) {
        clickCatalog(submenuLocator, CATALOG_MENU, null, 3);
    }

    public void openCategoriesPage() {
        openCatalog(CATEGORIES_SUBMENU);
    }

    public void openProductsPage() {
        openCatalog(PRODUCTS_SUBMENU);
    }

    public void clickSaveButton() {
        findAndClickElement(SAVE_BUTTON);
    }

    public void clickAddButton() {
        findAndClickElement(ADD_BUTTON);
    }

    public void enterNameAndMetaTag(String name, String metaTag) {
        findElement(PRODUCT_NAME_FIELD).sendKeys(name);
        findElement(META_TAG_TITLE_FIELD).sendKeys(metaTag);
    }

    public void productEnterSeoUrl(String seoUrl) {
        findAndClickElement(PRODUCT_SEO_TAB);
        findAndClickElement(SEO_URL_FIELD);
        findElement(SEO_URL_FIELD).sendKeys(seoUrl);
    }

    public void categoryEnterSeoUrl(String seoUrl) {
        findAndClickElement(CATEGORY_SEO_TAB);
        findAndClickElement(SEO_URL_FIELD);
        findElement(SEO_URL_FIELD).sendKeys(seoUrl);
    }

    public void enterModel(String modelName) {
        findAndClickElement(PRODUCT_DATA_TAB);
        findElement(MODEL_FIELD).sendKeys(modelName);
    }

    public void selectCategory(String categoryName) {
        findAndClickElement(PRODUCT_LINKS_TAB);
        findElement(CATEGORIES_FIELD).sendKeys(categoryName);
        findAndClickElement(DEVICES_DROPDOWN_MENU);
    }

    public void addNewCategory(String categoryName, String seoName) {
        clickAddButton();
        enterNameAndMetaTag(categoryName, categoryName);
        categoryEnterSeoUrl(seoName);
        clickSaveButton();
    }

    public void addNewProduct(String productName, String categoryName, String seoName) {
        clickAddButton();
        enterNameAndMetaTag(productName, productName);
        enterModel(productName);
        selectCategory(categoryName);
        productEnterSeoUrl(seoName);
        clickSaveButton();
    }

    public void search(String searchText) {
        findElement(SEARCH_FIELD).sendKeys(searchText);
        findAndClickElement(SEARCH_ICON);
    }

    public String devicesCategoryGetText() {
        return findElement(DEVICES_CATEGORY).getText();
    }

    public void clickSecondPageCategoriesTable() {
        findAndClickElement(CATEGORIES_TABLE_SECOND_PAGE);
    }

    public void clickSecondPageProductsTable() {
        findAndClickElement(PRODUCTS_TABLE_SECOND_PAGE);
    }

    public String getAlertText() {
        return findElement(ALERT).getText();
    }

    public String getSearchResultHeaderText() {
        return findElement(SEARCH_HEADER).getText();
    }
}