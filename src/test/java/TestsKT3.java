import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.jupiter.api.Assertions.*;

class TestsKT3 {

    private static WebDriver driver;
    private static Page productPage;

    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        productPage = new Page(driver);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @Story("Addition to wishlist")
    @Description("Addition to wishlist test")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Addition to wishlist")
    void testAddToWishlist(int productIndex) {
        productPage.openMainPage();
        productPage.clickProductName(productIndex, null, 3);
        productPage.findAndClickElement(Page.WISHLIST_ON_PRODUCT_PAGE_BUTTON, null, 3);
        assertTrue(productPage.isLoginAlertDisplayed());
    }

    @ParameterizedTest
    @ValueSource(strings = {" Success: You have added Canon EOS 5D to your shopping cart! "})
    void testAddCamera(String expected) throws InterruptedException {
        productPage.openMainPage();
        productPage.openCamerasPage();
        productPage.clickProductName(0, null, 3);
        productPage.openOptionalOptions();
        productPage.selectRedColor();
        productPage.findAndClickElement(Page.ADD_TO_CART_BUTTON, null, 3);

        if (productPage.isCartAlertDisplayed()) {
	        if (!productPage.isThisCartAlert(expected)) {
	            System.out.println("This is a wrong alert");
		        fail();
	        }
        } else {
            System.out.println("There was no alert");
	        fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {" Success: You have added Samsung Galaxy Tab 10.1 to your shopping cart! "})
    void testAddTablet(String expected) {
        productPage.openMainPage();
        productPage.openTabletsPage();
        productPage.clickProductName(0, null, 3);
        productPage.findAndClickElement(Page.ADD_TO_CART_BUTTON, null, 3);

        if (productPage.isCartAlertDisplayed()) {
	        if (!productPage.isThisCartAlert(expected)) {
	            System.out.println("This is a wrong alert");
		        fail();
	        }
        } else {
            System.out.println("There was no alert");
	        fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {" Success: You have added HTC Touch HD to your shopping cart! "})
    void testAddHtcPhone(String expected) {
        productPage.openMainPage();
        productPage.openPhonesPage();
        productPage.clickProductName(0, null, 3);
        productPage.findAndClickElement(Page.ADD_TO_CART_BUTTON, null, 3);

        if (productPage.isCartAlertDisplayed()) {
	        if (!productPage.isThisCartAlert(expected)) {
	            System.out.println("This is a wrong alert");
		        fail();
	        }
        } else {
            System.out.println("There was no alert");
	        fail();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, tvink_jabki, kryti_tovar_vsem_sevotyy, 5"
    })
    void testWriteReview(int productIndex, String name, String reviewText, int rating) throws InterruptedException {
        productPage.openMainPage();
        productPage.clickProductName(productIndex, null, 3);
        productPage.findAndClickElement(Page.REVIEWS_BUTTON, null, 3);
        productPage.writeAReview(name, reviewText);
        productPage.rateTheProduct(rating);
        productPage.findAndClickElement(Page.CONTINUE_REVIEW_BUTTON, null, 3);

        String actualReviewerName = productPage.getActualReviewerName();
        String actualReviewText = productPage.getActualReviewText();

        assertEquals(name, actualReviewerName);
        assertEquals(reviewText, actualReviewText);
    }
}