package com.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

import java.util.List;
import java.util.logging.Logger;

public class AmazonPage extends LaunchAmazonTest {
    Locator searchLoc = getPageBrowser().locator("//input[contains(@placeholder,'Search')]");
    Locator searchButtonLoc = getPageBrowser().locator("//div[contains(@class,'search-submit')]");
    Locator searchResults = getPageBrowser().locator("//div[contains(@data-cel-widget,'SEARCH_RESULTS')]//span[contains(text(),'iPhone')]");
    private String results="//h2[text()='Results']";
    private String searchIcon="//div[contains(@class,'search-icon')]";
    private String appleStore = "//a[contains(text(),'Apple Store')]";
    private String iconImg="//div[contains(@data-testid,'hero')]//img";
    private String searchAllApple="//input[contains(@placeholder,'Apple')]";

    static Page newTab;
    Boolean result;

    @Step("Validating search results")
    public void validateSearchResults(String entity) throws Exception {
        searchLoc.fill(entity);
        searchButtonLoc.click();
        getPageBrowser().waitForSelector(results,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
        List<Locator> resultLoc = searchResults.all();
        Boolean result = true;
        System.out.println("Checking search results");
        for (Locator loc : resultLoc) {
            if (!loc.textContent().contains("iPhone")){
                System.out.println("wrong search results");
                result = false;
            }
            break;
        }
        getSoftAssert().assertTrue(result, "Search Results are not matching");
    }
    @Step("Validating Apple store link")
    public void openAppleStoreFromProductPage(String entity) throws Exception {
        searchLoc.fill(entity);
        getPageBrowser().waitForSelector(searchIcon,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
        List<Locator> suggestionList = getPageBrowser().locator("//div[contains(@class,'suggestion-container')]//div[contains(text(),'iphone')]").all();
        for (Locator loc : suggestionList) {
            if (loc.textContent().equals(entity))
                loc.click();
            break;
        }
        newTab = getbrowsercontext().waitForPage(() -> {
            searchResults.first().click();
        });
        newTab.waitForSelector(appleStore,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
        newTab.locator("//a[contains(text(),'Apple Store')]").click();
    }
    @Step("verify product details in apple store")
    public void verifyProductInAppleStore(String entity, String entityType, String color) throws Exception {
        newTab.waitForSelector(searchAllApple,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
        newTab.locator("//nav[contains(@aria-label,'Navigation')]//span[text()='" + entityType + "']").first().click();
        newTab.waitForSelector(iconImg,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
        newTab.locator("//div[contains(@aria-label,'List')]//span[text()='" + entity + "']").click();
        newTab.locator("//a[contains(@title,'Apple Watch Series 9 [GPS 41mm]') and contains(@title,'RED')]/../..//button[contains(@aria-label,'Quick look')]").hover();
        newTab.locator("//a[contains(@title,'Apple Watch Series 9 [GPS 41mm]') and contains(@title,'RED')]/../..//button[contains(@aria-label,'Quick look')]").click();
        getSoftAssert().assertTrue(newTab.locator
                                ("//div[contains(@data-testid,'product-showcase')]//a[contains(@title,'Apple Watch Series 9 [GPS 41mm]') and contains(@title,'RED')]")
                        .isVisible(),
                "Product is not visible in Modal");
    }
}

