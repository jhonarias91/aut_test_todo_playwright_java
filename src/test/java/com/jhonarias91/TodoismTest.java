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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoismTest {
    private static final String URL = "http://localhost:5000/#intro";
    //private static final String URL= "https://todoism.onrender.com/#intro";

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll
    public void beforeAll() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
        this.page = browser.newPage();
    }

    @BeforeEach
    public void setUp() {
        this.loginPage = new LoginPage(this.page);
        this.page.navigate(URL);
        this.loginPage.goToLoginPage();
        this.loginPage.getTestAccount();
        page.waitForTimeout(1000);
        this.loginPage.login();
    }

    @Test
    //@RepeatedTest(10) //To verify flaky test
    public void testCreateTask() {
        mainPage = new MainPage(this.page);
        String taskName = "Review PR";
        mainPage.createNewTask(taskName);

        boolean newTaskAtTheEnd = mainPage.isActiveTaskAtTheEnd(taskName);
        assertTrue(newTaskAtTheEnd);
    }

    @Test
    public void testCreateAndCompleteTask() {
        mainPage = new MainPage(this.page);
        String taskName = "Update dependencies";
        mainPage.createNewTask(taskName);
        boolean newTaskAtTheEnd = mainPage.isActiveTaskAtTheEnd(taskName);
        assertTrue(newTaskAtTheEnd);

        mainPage.markAsCompletedByName(taskName);

        boolean newTaskIsInactive = mainPage.isInactiveTask(taskName);
        assertTrue(newTaskIsInactive);

    }

    @Test
    public void testCleanATask() {
        mainPage = new MainPage(this.page);
        String taskName = "Learn AWS!";
        mainPage.createNewTask(taskName);
        mainPage.isActiveTaskAtTheEnd(taskName);
        //Assume new Task created ok

        mainPage.markAsCompletedByName(taskName);

        mainPage.isInactiveTask(taskName);
        //Assume task is inactive now

        mainPage.clearAllInactiveTasks();
        boolean taskAtTheEnd = mainPage.isInactiveTaskDelete(taskName);
        assertTrue(taskAtTheEnd);
        //boolean allInactive = mainPage.isAnyInactive();

    }

    @AfterEach
    public void logOut() {
        //This is to be on the same page after each test
        mainPage.logOut();
    }

    @AfterAll
    public void close() {
        this.playwright.close();
    }
}
