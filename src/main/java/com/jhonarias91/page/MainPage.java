package com.jhonarias91.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class MainPage {

    private String SPAN_ACTIVE_TASK_SELECTOR = "//span[@class='active-item']";
    private String SPAN_INACTIVE_TASKS_SELECTOR = "//span[@class='inactive-item']";

    private Locator itemInput;
    private Locator btnClear;
    private Locator allTaskChecks;
    private Locator allActiveTasks;
    private Locator allInactiveTask;

    private Page page;

    private Locator logOut;

    public MainPage(Page page) {
        this.page = page;
        this.itemInput = page.locator("//header/input[@id='item-input']");
        this.btnClear = page.locator("//a[@id='clear-btn']");
        this.allTaskChecks = page.locator("//div[@class='items']//i[@class='material-icons left']");
        this.allActiveTasks = page.locator(SPAN_ACTIVE_TASK_SELECTOR);
        this.allInactiveTask = page.locator(SPAN_INACTIVE_TASKS_SELECTOR);
        this.logOut = page.locator("//a[@id='logout-btn' and @class='waves-effect waves-light']");
    }

    public void createNewTask(String taskName) {
        itemInput.fill(taskName);
        itemInput.press("Enter");
    }

    public boolean isActiveTaskAtTheEnd(String taskName) {
        // Wait for the element to be visible
        page.waitForSelector(String.format("//span[@class='active-item' and text()='%s']",taskName));
        int count = allActiveTasks.count();
        if (count == 0) {
            return false;
        }//nth give us the element at the position
        return allActiveTasks.nth(count - 1).innerText().equals(taskName);
    }

    public void logOut(){
        logOut.click();
    }

    public void markAsCompletedByName(String taskName){

        //One way to do this is by using the following line
        //page.locator(String.format("//span[@class='active-item' and text()='%s']/../a[@class='button done-btn']",taskName)).click();
        //But we can also use the following line
        Locator checkTask = page.locator(String.format("//span[@class='active-item' and text()='%s']/preceding-sibling::a[@class='button done-btn']", taskName));
        checkTask.scrollIntoViewIfNeeded();
        //We were getting and error here, because the element is always hide
        //checkTask.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        //The a element to mark as completed is hidden, so I need to force the click
        checkTask.click(new Locator.ClickOptions().setForce(true));
    }

    public boolean isInactiveTask(String taskName) {

        Locator inactiveTask = page.locator(String.format("//span[@class='inactive-item' and text()='%s']", taskName));
        inactiveTask.scrollIntoViewIfNeeded();
        inactiveTask.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        List<String> allInactiveTasks = allInactiveTask.allInnerTexts();

        return allInactiveTasks.contains(taskName);
    }

    public void clearAllInactiveTasks() {
        this.btnClear.click();
    }

    public boolean isInactiveTaskDelete(String taskName) {

        Locator spanByTaskName = page.locator(String.format("//div[@class='items']//span[contains(text(),'%s')]", taskName));

        //We need to wait for the element to be detached
        spanByTaskName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
        List<String> allInactiveTasks = spanByTaskName.allInnerTexts();

        if (allInactiveTasks == null || allInactiveTasks.isEmpty()){
            return true;
        }else{
            //At this point if some tasks are present and match exactly with the  taskName, those should be active
            for (String task : allInactiveTasks){
                if (task.equals(taskName)){
                    //If the task is inactive and the name is perfec match, this should be active
                    if (isInactiveTask(taskName)){
                        return false;
                    }
                }
            }
        }//At the end if not task is found from the active tasks, then it was deleted
        return true;
    }

    public boolean isAnyInactive(){

        return allInactiveTask.count() > 0;
    }
}
