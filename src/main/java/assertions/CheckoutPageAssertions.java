package assertions;

import businessPages.QA_CheckOut;

public class CheckoutPageAssertions extends Assertions{
    public CheckoutPageAssertions validateCertainCheckoutProductDetails(QA_CheckOut checkout , String productName
                                                                        , String expectedName, String expectedPrice
                                                                        , String expectedQty, String expectedTotalPrice)
    {
        assertEqual(checkout.getProductName(productName)
                ,expectedName,"Product name is not matching").
                assertEqual(checkout.getProductPrice(productName)
                        ,expectedPrice,"Product price is not matching").
                assertEqual(checkout.getProductQuantity(productName)
                        ,expectedQty,"Product Qty is not matching").
                assertEqual(checkout.getProductTotalPrice(productName)
                        ,expectedTotalPrice,"Product total price is not matching");
        return this;
    }

}
