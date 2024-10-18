package com.jhonarias91;


import com.jhonarias91.page.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.regex.Pattern;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoismTest
{

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;

    @BeforeAll
    public void beforeAll(){
        this.playwright = Playwright.create();
        this.browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
        this.page = browser.newPage();
    }

    @BeforeEach
    public void setUp(){
        this.loginPage = new LoginPage(this.page);
        this.page.navigate("http://127.0.0.1:5000/#login");
        this.loginPage.getTestAccount();
        this.loginPage.login();
    }

    @Test
    public void testCreateTask(){


    }

}
