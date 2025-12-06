package businessPages;

public interface IQA_ProductPage {


    public boolean isProductPageLoaded();
    public IQA_ProductPage addToCart(String productName);
    public IQA_ProductPage selectShopCategory();
    public boolean isCartCounterIncremented();
    public String getCertainProductName(String productName);
    public String getCertainProductPrice(String productName);
    public String getCertainProductType(String productName);
    public QA_ProductDetails clickOnCertainProduct(String productName);
    public QA_Cart clickOnCartIcon();
    public IQA_ProductPage selectTypeFilter(String type);
    public IQA_ProductPage selectPriceFilter(String price);
    public boolean isProductPricesInFilterRange(String priceRange);
    public String getFirstProductType();
    public String getLastProductType();
    default IQA_ProductPage selectColorFilter(String color){return this;}
    default IQA_ProductPage selectSizeFilter(String size){return this;}
    default IQA_ProductPage selectBrandFilter(String brand){return this;}
    default String getCertainProductSize(String productName){return"";}
    default String getCertainProductColor(String productName){return"";}
    default String getFirstProductSize(){return "";}
    default String getFirstProductColor(){return"";}
    default String getLastProductColor(){return "";}
    default String getLastProductSize(){return "";}
    default String getProductBrand(){return "";}
}
