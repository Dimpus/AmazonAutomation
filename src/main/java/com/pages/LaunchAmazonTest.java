package com.pages;

import com.microsoft.playwright.*;
import configs.AutoConfigs;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;


public class LaunchAmazonTest {
    public static LaunchAmazonTest instance;
    public static SoftAssert softAssert;
    public static Playwright playwright;
    private static Page pagebrowser;
    private static Browser browser;
    private static BrowserContext browsercontext;

    public static LaunchAmazonTest getInstance() {
        if (instance == null) {
            instance = new LaunchAmazonTest();
        }
        softAssert = new SoftAssert();

        return instance;
    }

    public SoftAssert getSoftAssert() {
        return softAssert;
    }

    public Page getPageBrowser() {
        return pagebrowser;
    }

    public BrowserContext getbrowsercontext() {
        return browsercontext;
    }

    public Browser getBrowser() {
        return browser;
    }

    @Step("lanuch Amazon Application")
    public void launchAmazon() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setChannel("chrome")
                .setHeadless(false));
        browsercontext = browser.newContext();
        pagebrowser = browsercontext.newPage();
        pagebrowser.navigate(AutoConfigs.amazonEnv);
    }

    @AfterMethod
    public void afterMethod() {
        getbrowsercontext().close();
        getPageBrowser().close();
    }
}
