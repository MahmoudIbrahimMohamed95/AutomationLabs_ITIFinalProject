package businessPages;

import businessPages.common.QA_CommonFilters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import readers.Log;
import utilActions.ElementActions;

public class QA_Electronics implements IQA_ProductPage {

    private static final By dropDownShop = By.xpath("//a[@class='nav-link dropdown-toggle' and normalize-space()='Shop']");
    private static final By menDropSelection=By.xpath("//a[normalize-space()='Mens Wear']");

    private static final By cartIcon = By.cssSelector("a>i.fa-2x");
    private static final By cartCounter = By.id("cartCount");
    private static final By logOutBtn = By.id("logoutBtn");
    private static final By breadcrumbLocator = By.cssSelector("span.breadcrumb-item");
    private static final By backBtn = By.cssSelector(".fa-backward");

    //Locators

    private static final By allProductNames = By.cssSelector(".text-truncate");
    private static final By allProductPrices = By.cssSelector("div.mt-2>h5");
    private static final By allProductSizeAndColors = By.cssSelector("div+small");
    private static final By allProductTypes = By.cssSelector("br+small");
    //Attributes
    private static WebDriver driver;
    private static ElementActions actions;
    private static short postCartCounter = 0;

    /***********************************************************************
     *      XPATH Is NOT A BEST APPROACH AND NOT ReCOMMENDED I JUST USED IT AS I WORK
     *      IN ALREADY DEVELOPED WEBSITE
     ************************************************************************/

    private static By productTypeByIndex(String index) {
        return By.xpath(String.format("(//div[@class='text-center py-4']//small[2])[%s]", index));
    }
    private static By productTypeFilterLocator(String filter){
        return By.xpath(String.format("//label[text()=' %s']", filter));
    }


    private static By productPriceFilterLocator(String filter){
        return By.xpath(String.format("//label[text()='%s']", filter));
    }
    private static By productBrandFilterLocator(String filter){
        return By.xpath(String.format("//label[text()=' %s']", filter));
    }


    private static By productPriceByIndex(String index) {
        return By.xpath(String.format("(//div[@class='d-flex align-items-center justify-content-center mt-2']//h5)[%s]", index));
    }

    private static By certainProductNameLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']", productName));
    }

    private static By certainProductPriceLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following-sibling::div[contains(@class, 'd-flex')]//h5", productName));
    }


    private static By certainProductTypeLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following-sibling::small[2]", productName));
    }
    private static By productBrandByIndex(String index) {
        return By.xpath(String.format("(//div[@class='text-center py-4']//small[1])[%s]", index));
    }
    private static By addToCartBtnLocator(String productName) {
        return By.xpath(String.format("//a[.='%s']/following::button[contains(@class, 'addToCart')][1]", productName));
    }

    //constructor
    public QA_Electronics(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
      }

    //Methods
    public QA_Shop clickOnBackBtn() {
        actions.click(backBtn);
        return new QA_Shop(driver);
    }

    @Override
    public QA_Electronics addToCart(String productName) {
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

    public boolean isProductPageLoaded() {
        Log.info("Product page is loaded");
        return actions.getText(breadcrumbLocator).contains("Electronics");
    }
    public QA_Electronics selectPriceFilter(String price) {
        actions.click(productPriceFilterLocator(price));
        return this;
    }
    public QA_Electronics selectTypeFilter(String filter) {
        actions.click(productTypeFilterLocator(filter));
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
    public IQA_ProductPage selectBrandFilter(String brand){
       actions.click(productBrandFilterLocator(brand));
       return this;
    }
    public String getProductBrand(){
        return actions.getText(productBrandByIndex("1")).split(":")[1].trim();
    }
    public QA_MenFashion selectShopCategory() {
        actions.hover(dropDownShop)
                .click(dropDownShop)
                .click(dropDownShop)
                .click(menDropSelection);
        return new QA_MenFashion(driver);
    }

}

