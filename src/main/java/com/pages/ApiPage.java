package com.pages;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;

public class ApiPage extends LaunchAmazonTest {
    public APIRequestContext apiConfig(){
        APIRequest apiReq=playwright.request();
        APIRequestContext apiContext=apiReq.newContext();
        return apiContext;
    }
}
