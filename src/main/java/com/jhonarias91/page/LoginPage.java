package com.jhonarias91.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {

    private Locator userName;

    private Locator btnLogin;
    private Locator btnMainLogin;
    private Locator btnGetTestAccount;
    private Page page;

    public LoginPage(Page page) {
        this.page = page;
        this.btnGetTestAccount = page.locator("//a[@id='register-btn']");
        this.userName = page.locator("//input[@id='username-input' and @name='username']");
        this.btnLogin = page.locator("//a[@id='login-btn']");
        this.btnMainLogin = page.locator("//a[@class='btn waves-effect waves-light red']");
    }

    public void goToLoginPage(){
        btnMainLogin.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        this.btnMainLogin.click();
    }
    public void getTestAccount(){
        this.btnGetTestAccount.click();
    }

    public void login(){
        this.btnLogin.click();
    }

    public Locator getUserNameLocator() {
        return userName;
    }
}
