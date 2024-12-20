package com.jhonarias91;

import com.jhonarias91.page.LoginPage;
import com.jhonarias91.page.MainPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoismTest {
    private static final String URL = "http://localhost:5000/#intro";
    private static final boolean ENABLE_TRACING = false;
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
                        .setHeadless(true));
        this.page = browser.newPage();
        page.setDefaultTimeout(120000);
    }

    @BeforeEach
    public void setUp() {
        //Enabling this for each test is lest time consuming thant doing it on the beforeAll
        if (ENABLE_TRACING){
            page.context().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(false) // get screenshots during tracing, this is high time consuming
                    .setSnapshots(false)   // Get dom and snaptshots
                    .setSources(true));
        }
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
    }

    @AfterEach
    public void logOut() {
        if (ENABLE_TRACING) {
            page.context().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace.zip")));
        }
        //This is to be on the same page after each test
        mainPage.logOut();
    }

    @AfterAll
    public void close() {
        this.playwright.close();
    }
}
