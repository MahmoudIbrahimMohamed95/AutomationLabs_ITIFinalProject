package assertions;

import businessPages.QA_Confirmation;

public class ConfirmationPageAssertions extends Assertions{
    public ConfirmationPageAssertions validateConfirmationDetails(QA_Confirmation confirm,
                                                                   String expectedFName, String expectedMName, String expectedLName, String mailAddress,
                                                                   String expectedMobileNumber, String expectedAddress ,
                                                                   String expectedState, String expectedCity, String expectedPinCode)
    {

        assertEqual(confirm.getFirstName()
                ,expectedFName,"first name is not matching").
                assertEqual(confirm.getMiddleName()
                        ,expectedMName,"middle name is not matching").
                assertEqual(confirm.getLastName()
                        ,expectedLName,"last name is not matching").
                assertEqual(confirm.getPhone()
                        ,expectedMobileNumber,"phone is not matching").
                assertEqual(confirm.getAddress()
                        ,expectedAddress,"Address is not matching").
                assertEqual(confirm.getState()
                        ,expectedState,"State is not matching").
                assertEqual(confirm.getCity()
                        ,expectedCity,"City is not matching").
                assertEqual(confirm.getPinCode()
                        ,expectedPinCode,"Pincode is not matching");
        return this;
    }
    public Assertions validateConfirmationOfCertainProductDetails(QA_Confirmation confirm , String productName
                                                                  , String expectedName, String expectedPrice
                                                                  , String expectedQty, String expectedTotalPrice)

    {
        assertEqual(confirm.getProductName(productName)
                ,expectedName,"Product name is not matching").
                assertEqual(confirm.getProductPrice(productName)
                        ,expectedPrice,"Product price is not matching").
                assertEqual(confirm.getProductQuantity(productName)
                        ,expectedQty,"Product Qty is not matching").
                assertEqual(confirm.getProductTotalPrice(productName)
                        ,expectedTotalPrice,"Product total price is not matching");

        return this;
    }

}
