package businessPages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_CommonFilters {


    private static final By  formalTypeFilter=By.xpath("//label[text()=' Formal']");
    private static final By  footWearTypeFilter=By.cssSelector("//label[text()=' Footwear']");

    private static final By zeroToHundredPriceFilter=By.xpath("//label[text()='$0 - $100']");
    private static final By HundredOneToTwoHundredPriceFilter=By.xpath("//label[text()='$101 - $200']");
    private static final By TwoHundredOneToThreeHundredPriceFilter=By.xpath("//label[text()='$201 - $300']");
    private static final By ThreeHundredOneToFourHundredPriceFilter=By.xpath("//label[text()='$301 - $400']");
    private static final By FourHundredOneToFiveHundredPriceFilter=By.xpath("//label[text()='$401 - $500']");

    private static final By SmallSizeFilter=By.xpath("//label[text()=' Small']");
    private static final By MediumSizeFilter=By.xpath("//label[text()=' Medium']");
    private static final By LargeSizeFilter=By.xpath("//label[text()=' Large']");
    private static final By ExtraLargeSizeFilter=By.xpath("//label[text()=' Extra Large']");
    private static final By DoubleExtraSizeFilter=By.xpath("//label[text()=' Double Extra Large']");

    private static final By BlackColorFilter=By.xpath("//label[text()=' Black']");
    private static final By WhiteColorFilter=By.xpath("//label[text()=' White']");
    private static final By RedColorFilter=By.xpath("//label[text()=' Red']");
    private static final By BlueColorFilter=By.xpath("//label[text()=' Blue']");
    private static final By GreenColorFilter=By.xpath("//label[text()=' Green']");

    //attributes
    private WebDriver driver;
    private ElementActions actions;

    //constructor
    public QA_CommonFilters(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

    /*******************************************************
     *
     * USING SWITCH CASES BY THIS WAY AGAINST SOLID PRINCIPLES
     * OPEN TO EXTEND CLOSED TO MODIFY
     * BUT JUST FOR PRACTICE
     *
     ********************************************************/
    //methods
    public void selectPriceFilter(String priceFilter) {
        switch (priceFilter) {
            case "$0 - $100" ->actions.click(zeroToHundredPriceFilter);
            case "$101 - $200" ->actions.click(HundredOneToTwoHundredPriceFilter);
            case "$201 - $300" ->actions.click(TwoHundredOneToThreeHundredPriceFilter);
            case "$301 - $400" ->actions.click(ThreeHundredOneToFourHundredPriceFilter);
            case "$401 - $500" ->actions.click(FourHundredOneToFiveHundredPriceFilter);
        default -> throw new UnsupportedOperationException("Price filter not supported");
        }
    }
    public void selectTypeFilter(String typeFilter) {
        switch (typeFilter.toUpperCase()) {
            case "FORMAL" ->actions.click(formalTypeFilter);
            case "FOOTWEAR" ->actions.click(footWearTypeFilter);
            default -> throw new UnsupportedOperationException("Type filter not supported");
        }
    }
    public void selectSizeFilter(String sizeFilter) {
        switch (sizeFilter.toUpperCase()) {
            case "SMALL" ->actions.click(SmallSizeFilter);
            case "MEDIUM" ->actions.click(MediumSizeFilter);
            case "LARGE" ->actions.click(LargeSizeFilter);
            case "EXTRA_LARGE" ->actions.click(ExtraLargeSizeFilter);
            case "DOUBLE_EXTRA_LARGE" ->actions.click(DoubleExtraSizeFilter);
            default -> throw new UnsupportedOperationException("Size filter not supported");
        }
    }
    public void selectColorFilter(String colorFilter) {
        switch (colorFilter.toUpperCase()) {
            case "BLACK" ->actions.click(BlackColorFilter);
            case "WHITE" ->actions.click(WhiteColorFilter);
            case "RED" ->actions.click(RedColorFilter);
            case "BLUE" ->actions.click(BlueColorFilter);
            case "GREEN" ->actions.click(GreenColorFilter);
            default -> throw new UnsupportedOperationException("Color filter not supported");
        }
    }

}
