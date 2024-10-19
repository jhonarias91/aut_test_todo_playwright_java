package com.jhonarias91.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {

    private String SPAN_NAMES_SELECTOR = "//span[@class='active-item']";

    private Locator itemInput;
    private Locator btnClear;

    private Locator allTaskChecks;
    private Locator allActiveTaskNames;

    private Page page;

    private Locator logOut;

    public MainPage(Page page) {
        this.page = page;
        this.itemInput = page.locator("//header/input[@id='item-input']");
        this.btnClear = page.locator("//a[@id='clear-btn']");
        this.allTaskChecks = page.locator("//div[@class='items']//i[@class='material-icons left']");
        this.allActiveTaskNames = page.locator(SPAN_NAMES_SELECTOR);
        this.logOut = page.locator("//a[@id='logout-btn' and @class='waves-effect waves-light']");
    }

    public void createNewTask(String taskName) {
        itemInput.fill(taskName);
        itemInput.press("Enter");
    }

    public boolean isNewTaskAtTheEnd(String taskName) {
        // Wait for the element to be visible
        page.waitForSelector(String.format("//span[@class='active-item' and text()='%s']",taskName));
        //Check if the last element is on the list of active task, also we can use allTaskNames.allInnerTexts()
        return allActiveTaskNames.nth(allActiveTaskNames.count() - 1).innerText().equals(taskName);
    }

    public void logOut(){
        logOut.click();
    }
}
