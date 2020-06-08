package com.etorobot.pages;

import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EToroLoginPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope header.a-header.-head-grad div.w-head-buttons-ph.mobile-no-leng-name et-language-picker a")
    @CacheLookup
    private WebElement english;

    @FindBy(css = "button.sign-up-button.sign-up-social-button.facebook-button.ng-scope")
    @CacheLookup
    private WebElement facebook;

    @FindBy(css = "button.sign-up-button.sign-up-social-button.google-button.ng-scope")
    @CacheLookup
    private WebElement google;

    private final String pageLoadedText = "";

    private final String pageUrl = "/login";

    @FindBy(id = "password")
    @CacheLookup
    private WebElement password;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(1) login.ng-scope.ng-isolate-scope login-sts.ng-isolate-scope div.login-wrapper.ng-scope div.w-login div.w-login-main form.ng-pristine.ng-valid-email.ng-invalid.ng-invalid-required.ng-valid-pattern.ng-valid-maxlength div.w-login-main-form.login-screen div:nth-of-type(5) button")
    @CacheLookup
    private WebElement signIn;

    @FindBy(id = "sign-up-link")
    @CacheLookup
    private WebElement signUp;

    @FindBy(id = "CB")
    @CacheLookup
    private WebElement staySignedIn;

    @FindBy(id = "username")
    @CacheLookup
    private WebElement usernameOrEmail;

    public EToroLoginPage() {
    }

    public EToroLoginPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public EToroLoginPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public EToroLoginPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on English Link.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage clickEnglishLink() {
        english.click();
        return this;
    }

    /**
     * Click on Facebook Button.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage clickFacebookButton() {
        facebook.click();
        return this;
    }

    /**
     * Click on Google Button.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage clickGoogleButton() {
        google.click();
        return this;
    }

    /**
     * Click on Sign In Button.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage clickSignInButton() {
        signIn.click();
        return this;
    }

    /**
     * Click on Sign Up Link.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage clickSignUpLink() {
        signUp.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage fill() {
        setUsernameOrEmailEmailField();
        setPasswordPasswordField();
        setStaySignedInCheckboxField();
        return this;
    }

    /**
     * Fill every fields in the page and submit it to target page.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage fillAndSubmit() {
        fill();
        return submit();
    }

    /**
     * Set default value to Password Password field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage setPasswordPasswordField() {
        return setPasswordPasswordField(data.get("PASSWORD"));
    }

    /**
     * Set value to Password Password field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage setPasswordPasswordField(String passwordValue) {
        password.sendKeys(passwordValue);
        return this;
    }

    /**
     * Set Stay Signed In Checkbox field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage setStaySignedInCheckboxField() {
        if (!staySignedIn.isSelected()) {
            staySignedIn.click();
        }
        return this;
    }

    /**
     * Set default value to Username Or Email Email field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage setUsernameOrEmailEmailField() {
        return setUsernameOrEmailEmailField(data.get("USERNAME_OR_EMAIL"));
    }

    /**
     * Set value to Username Or Email Email field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage setUsernameOrEmailEmailField(String usernameOrEmailValue) {
        usernameOrEmail.sendKeys(usernameOrEmailValue);
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage submit() {
        clickSignInButton();
        return this;
    }

    /**
     * Unset Stay Signed In Checkbox field.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage unsetStaySignedInCheckboxField() {
        if (staySignedIn.isSelected()) {
            staySignedIn.click();
        }
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the eToroLoginPage class instance.
     */
    public EToroLoginPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
