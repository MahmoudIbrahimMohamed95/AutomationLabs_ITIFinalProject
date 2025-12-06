package assertions;

import businessPages.IQA_ProductPage;

public class ProductPageAssertions extends Assertions {

    public ProductPageAssertions validateCertainProductDetails(IQA_ProductPage product, String productName
            , String expectedName, String expectedPrice
            , String expectedSize, String expectedColor, String expectedType) {
        assertEqual(product.getCertainProductName(productName)
                , expectedName, "Product name is not matching").
                assertEqual(product.getCertainProductPrice(productName)
                        , expectedPrice, "Product price is not matching").
                assertEqual(product.getCertainProductSize(productName)
                        , expectedSize, "Product size is not matching").
                assertEqual(product.getCertainProductColor(productName)
                        , expectedColor, "Product color is not matching").
                assertEqual(product.getCertainProductType(productName)
                        , expectedType, "Product type is not matching");
        return this;
    }

    public ProductPageAssertions validateProductsPrice(IQA_ProductPage product, String PriceFilter) {
        assertTrue(product.isProductPricesInFilterRange(PriceFilter), "Prices not in filter range");
        return this;
    }
    public ProductPageAssertions validateProductsType(IQA_ProductPage product, String TypeFilter) {
        assertEqual(product.getFirstProductType(), TypeFilter, "FirstProduct type is not matching")
                .assertEqual(product.getLastProductType(), TypeFilter, "LastProduct type is not matching");
        return this;
    }

    public ProductPageAssertions validateProductsSize(IQA_ProductPage product, String SizeFilter) {
        assertEqual(product.getFirstProductSize(), SizeFilter, "FirstProduct size is not matching")
                .assertEqual(product.getLastProductSize(), SizeFilter, "LastProduct size is not matching");
        return this;
    }

    public ProductPageAssertions validateProductsColor(IQA_ProductPage product, String ColorFilter) {
        assertEqual(product.getFirstProductColor(), ColorFilter, "FirstProduct color is not matching")
                .assertEqual(product.getLastProductColor(), ColorFilter, "LastProduct color is not matching");
        return this;
    }
    public ProductPageAssertions validateProductsBrand(IQA_ProductPage product, String BrandFilter) {
        assertEqual(product.getProductBrand(), BrandFilter, "FirstProduct color is not matching");
        return this;
    }
}