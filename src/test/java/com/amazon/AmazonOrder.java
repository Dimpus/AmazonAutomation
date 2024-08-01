package com.amazon;

import com.microsoft.playwright.APIResponse;
import com.pages.AmazonPage;
import com.pages.ApiPage;
import com.pages.LaunchAmazonTest;
import configs.AutoConfigs;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(AllureTestNg.class)
public class AmazonOrder {
    @Test
    public void automateAmazon() throws Exception {
        try {
            LaunchAmazonTest launchAmazonTest = LaunchAmazonTest.getInstance();
            launchAmazonTest.launchAmazon();
            AmazonPage amazonPage = new AmazonPage();
            amazonPage.validateSearchResults("iphone13");
            amazonPage.openAppleStoreFromProductPage("iphone 13 128 gb");
            amazonPage.verifyProductInAppleStore("Apple Watch Series 9 (GPS)", "Apple Watch", "RED");
            launchAmazonTest.getSoftAssert().assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void callApi() throws Exception {
        ApiPage api = new ApiPage();
        APIResponse apiResponse = api.apiConfig().get(AutoConfigs.apiUrl);
        System.out.println(apiResponse.status());
        //we can use Jsonpath also to iterate through response. I have used Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
        System.out.println(jsonNode.toPrettyString());
    }
}
