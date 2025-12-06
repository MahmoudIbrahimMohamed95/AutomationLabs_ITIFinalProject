package assertions;

import businessPages.QA_Cart;

public class CartPageAssertions extends Assertions{
    public  CartPageAssertions validateCertainCartProductDetails(QA_Cart cart , String productName
            , String expectedName, String expectedPrice
            , String expectedQty, String expectedTotalPrice)
    {
        assertEqual(cart.getProductName(productName)
                    ,expectedName,"Product name is not matching")
        .assertEqual(cart.getProductPrice(productName)
                     ,expectedPrice,"Product price is not matching")
        .assertEqual(cart.getProductQuantity(productName)
                     ,expectedQty,"Product Qty is not matching")
        .assertEqual(cart.getProductTotalPrice(productName)
                     ,expectedTotalPrice,"Product total price is not matching");
        return this;
    }

}
