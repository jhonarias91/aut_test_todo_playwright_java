package com.jhonarias91;


import com.jhonarias91.page.LoginPage;
import com.jhonarias91.page.MainPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoismTest{

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll
    public void beforeAll(){
        this.playwright = Playwright.create();
        this.browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(true));
        this.page = browser.newPage();
    }

    @BeforeEach
    public void setUp(){
        this.loginPage = new LoginPage(this.page);
        this.page.navigate("http://127.0.0.1:5001/#login");
        this.loginPage.getTestAccount();
        page.waitForTimeout(1000);
        this.loginPage.login();
    }

    @Test
    //@RepeatedTest(10) //To verify flaky test
    public void testCreateTask(){
        mainPage = new MainPage(this.page);
        String taskName = "Review PR";
        mainPage.createNewTask(taskName);

        boolean newTaskAtTheEnd = mainPage.isNewTaskAtTheEnd(taskName);
        assertTrue(newTaskAtTheEnd);
        mainPage.logOut();
    }

    @AfterAll
    public void close(){
        this.playwright.close();
    }

}
