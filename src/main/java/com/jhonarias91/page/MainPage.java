package com.jhonarias91.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class MainPage {

    private Locator itemInput;
    private Locator btnClear;

    private List<Locator> allTaskChecks;

    private Page page;

    public MainPage(Page page) {
        this.page = page;
        this.itemInput = page.locator("//header/input[@id='item-input']");
        this.btnClear = page.locator("//a[@id='clear-btn']");
        this.allTaskChecks = page.locator("//div[@class='items']//i[@class='material-icons left']").all();

    }

  public void createNewTask(String taskName){
      itemInput.fill(taskName);
      itemInput.press("Enter");
  }
}
