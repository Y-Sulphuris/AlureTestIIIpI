import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestsKT5 {
    private static RemoteWebDriver driver;
    private static AdminPanel adminPanel;

    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        adminPanel = new AdminPanel(driver);
    }

    @ParameterizedTest
	@CsvSource({"Devices,devices"})
    public void testAddDevicesCategory(String categoryName, String seoName) throws InterruptedException {
        adminPanel.openAdminPage();
        adminPanel.login();
        Thread.sleep(1000);
        adminPanel.login();
        adminPanel.openCategoriesPage();
        adminPanel.addNewCategory(categoryName, seoName);
        adminPanel.openStartPage();
        Thread.sleep(1000);
        adminPanel.openCategoriesPage();
        adminPanel.clickSecondPageCategoriesTable();
        Thread.sleep(1000);
        String devicesCategory = adminPanel.devicesCategoryGetText();
        assertEquals(devicesCategory, categoryName+"\nEnabled");
    }
	
	@ParameterizedTest
	@CsvSource({
			"Keyboard 1,Keyboard 1,Keyboard 1,keyboard-1",
			"Keyboard 2,Keyboard 2,Keyboard 2,keyboard-2",
			"Mouse 1,Mouse 1,Mouse 1,mouse-1",
			"Mouse 2,Mouse 2,Mouse 2,mouse-2"
	})
    public void testAddProduct(String name, String metaTag, String model, String seoUrl) throws InterruptedException {
        adminPanel.openAdminPage();
        adminPanel.login();
        Thread.sleep(1000);
        adminPanel.login();
        adminPanel.openProductsPage();
        adminPanel.clickAddButton();
        adminPanel.enterNameAndMetaTag(name, metaTag);
        adminPanel.enterModel(model);
        adminPanel.selectCategory("Devices");
        adminPanel.productEnterSeoUrl(seoUrl);
        adminPanel.clickSaveButton();
        String actual = adminPanel.getAlertText();
        assertTrue(actual.equals("Success: You have modified products!") ||
                actual.equals("Warning: Please check the form carefully for errors!"));
    }
	
	@ParameterizedTest
	@ValueSource(strings = {"Keyboard 1", "Keyboard 2"})
    public void testSearchAddedProducts(String searchText) {
        adminPanel.openMainPage();
        adminPanel.search(searchText);
        assertEquals(adminPanel.getSearchResultHeaderText(), "Search - " + searchText);
    }

    @Test
    public void testDeleteProducts() throws InterruptedException {
        adminPanel.openAdminPage();
        adminPanel.login();
        Thread.sleep(1000);
        adminPanel.login();
        adminPanel.openProductsPage();
        adminPanel.clickSecondPageProductsTable();
        List<String> productNames = Arrays.asList("Keyboard 1", "Mouse 1");
        adminPanel.deleteProductsByName(
                productNames,
                AdminPanel.TABLE_PRODUCT_ROW,
                AdminPanel.DELETE_CHECKBOX,
                AdminPanel.DELETE_PRODUCT_BUTTON,
                AdminPanel.TABLE_PRODUCT_NAME,
                null
        );
        String actual = adminPanel.getAlertText();
        assertEquals(actual, "Success: You have modified products!");
    }
	
	@ParameterizedTest
	@ValueSource(strings = {"Keyboard 2", "Mouse 2"})
    public void searchDeletedProducts(String searchText) {
        adminPanel.openMainPage();
        adminPanel.search(searchText);
        assertEquals(adminPanel.getSearchResultHeaderText(), "Search - " + searchText);
    }

    @AfterAll
	static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}