package businessPages;

import businessPages.common.QA_CommonFilters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import readers.Log;
import utilActions.ElementActions;

public class QA_WomenFashion implements IQA_ProductPage {


    //locators
    private static final By cartIcon = By.cssSelector("a>i.fa-2x");
    private static final By cartCounter = By.id("cartCount");
    private static final By logOutBtn = By.id("logoutBtn");
    private static final By breadcrumbLocator = By.cssSelector("span.breadcrumb-item");
    private static final By backBtn = By.cssSelector(".fa-backward");

    //Locators
    private static By productTypeByIndex(String index) {
        return By.xpath(String.format("(//div[@class='text-center py-4']//small[2])[%s]", index));
    }

    private static By productSizeAndColorByIndex(String index) {
        return By.xpath(String.format("(//div[@class='text-center py-4']//small[1])[%s]", index));
    }

    private static By productPriceByIndex(String index) {
        return By.xpath(String.format("(//div[@class='d-flex align-items-center justify-content-center mt-2']//h5)[%s]", index));
    }

    private static final By allProductNames = By.cssSelector(".text-truncate");
    private static final By allProductPrices = By.cssSelector("div.mt-2>h5");
    private static final By allProductSizeAndColors = By.cssSelector("div+small");
    private static final By allProductTypes = By.cssSelector("br+small");
    private static final By dropDownShop = By.xpath("//a[@class='nav-link dropdown-toggle' and normalize-space()='Shop']");
    private static final By kidsDropSelection=By.xpath("//a[normalize-space()='Kids Wear']");
    //Attributes
    private static WebDriver driver;
    private static ElementActions actions;
    private static QA_CommonFilters commonFilters;
    private static short postCartCounter = 0;

    /***********************************************************************
     *      XPATH Is NOT A BEST APPROACH AND NOT ReCOMMENDED I JUST USED IT AS I WORK
     *      IN ALREADY DEVELOPED WEBSITE
     ************************************************************************/
    private static By certainProductNameLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']", productName));
    }

    private static By certainProductPriceLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following-sibling::div[contains(@class, 'd-flex')]//h5", productName));
    }

    private static By certainProductSizeAndColorLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following-sibling::small[1]", productName));
    }

    private static By certainProductTypeLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following-sibling::small[2]", productName));
    }

    private static By addToCartBtnLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following::button[contains(@class, 'addToCart')][1]", productName));
    }

    //constructor
    public QA_WomenFashion(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
        commonFilters = new QA_CommonFilters(driver);
    }

    //Methods
    public QA_Shop clickOnBackBtn() {
        actions.click(backBtn);
        return new QA_Shop(driver);
    }

    @Override
    public QA_WomenFashion addToCart(String productName) {
        actions.click(addToCartBtnLocator(productName));
        postCartCounter++;
        return this;
    }

    @Override
    public String getCertainProductName(String productName) {
        return actions.getText(certainProductNameLocator(productName));
    }

    @Override
    public String getCertainProductPrice(String productName) {
        return actions.getText(certainProductPriceLocator(productName));
    }

    @Override
    public String getCertainProductSize(String productName) {
        return actions.getText(certainProductSizeAndColorLocator(productName))
                .split("\\|")[0].split(":")[1].trim();
    }

    @Override
    public String getCertainProductColor(String productName) {
        return actions.getText(certainProductSizeAndColorLocator(productName))
                .split("Color:")[1].trim();

    }

    @Override
    public String getCertainProductType(String productName) {
        return actions.getText(certainProductTypeLocator(productName));
    }


    public QA_ProductDetails clickOnCertainProduct(String productName) {
        actions.click(certainProductNameLocator(productName));
        return new QA_ProductDetails(driver);
    }

    private static String getCartCounter() {
        return actions.getText(cartCounter);

    }

    public boolean isCartCounterIncremented() {
        return (Short.parseShort(getCartCounter()) == postCartCounter) ? true : false;
    }

    public QA_Cart clickOnCartIcon() {
        actions.click(cartIcon);
        return new QA_Cart(driver);
    }

    @Override

    //HINT
    public boolean isProductPageLoaded() {
        Log.info("Product page is loaded");
        return actions.getText(breadcrumbLocator).contains("Womens Wear");
    }


    public QA_WomenFashion selectColorFilter(String color) {
        commonFilters.selectColorFilter(color);
        return this;
    }

    public QA_WomenFashion selectSizeFilter(String size) {
        commonFilters.selectSizeFilter(size);
        return this;
    }

    public QA_WomenFashion selectPriceFilter(String price) {
        commonFilters.selectPriceFilter(price);
        return this;
    }

    public QA_WomenFashion selectTypeFilter(String filterType) {
        commonFilters.selectTypeFilter(filterType);
        return this;
    }

    public boolean isProductPricesInFilterRange(String priceRange) {
        int size = actions.getWebElements(allProductPrices).size();

        short boundryOne = Short.parseShort(priceRange.split("-")[0].split("\\$")[1].trim());
        short boundryTwo = Short.parseShort(priceRange.split("-")[1].split("\\$")[1].trim());

        short firstProductPrice = Short.parseShort(actions.getText(productPriceByIndex("1")).split("\\$")[1].trim());
        short lastProductPrice = Short.parseShort(actions.getText(productPriceByIndex(String.valueOf(size))).split("\\$")[1].trim());

        if ((firstProductPrice >= boundryOne && firstProductPrice <= boundryTwo)
                && (lastProductPrice >= boundryOne && lastProductPrice <= boundryTwo)) {
            return true;
        }
        return false;
    }

    public String getFirstProductType() {
        return actions.getText(productTypeByIndex("1")).split("-")[1].trim();

    }
    public String getLastProductType() {
        int size=actions.getWebElements(allProductTypes).size();
        return actions.getText(productTypeByIndex(String.valueOf(size))).split("-")[1].trim();
    }

    public String getFirstProductSize() {
        return actions.getText(productSizeAndColorByIndex("1")).split("\\|")[0].split(":")[1].trim();
    }

    public String getLastProductSize() {
        int size = actions.getWebElements(allProductSizeAndColors).size();
        return actions.getText(productSizeAndColorByIndex(String.valueOf(size))).split("\\|")[0].split(":")[1].trim();
    }

    public String getFirstProductColor() {
        return actions.getText(productSizeAndColorByIndex("1")).split("Color:")[1].trim();
    }

    public String getLastProductColor() {
        int size = actions.getWebElements(allProductSizeAndColors).size();
        return actions.getText(productSizeAndColorByIndex(String.valueOf(size))).split("Color:")[1].trim();
    }

    public QA_KidsFashion selectShopCategory(){
        actions.hover(dropDownShop)
                .click(dropDownShop)
                .click(dropDownShop)
               .click(kidsDropSelection);
        return new QA_KidsFashion(driver);
    }

}

