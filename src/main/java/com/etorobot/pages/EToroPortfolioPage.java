package com.etorobot.pages;

import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EToroPortfolioPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(1)")
    @CacheLookup
    private WebElement all;

    @FindBy(css = "a[href='/portfolio/chart']")
    @CacheLookup
    private WebElement chartView;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(6)")
    @CacheLookup
    private WebElement commodities;

    @FindBy(css = "a.uncomplete-button.button-standard.button-blue.ng-star-inserted")
    @CacheLookup
    private WebElement completeProfile;

    @FindBy(css = "a[href='/discover/people']")
    @CacheLookup
    private WebElement copyPeople;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(3)")
    @CacheLookup
    private WebElement copyportfolios;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(9)")
    @CacheLookup
    private WebElement crypto;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(7)")
    @CacheLookup
    private WebElement currencies;

    @FindBy(css = "a.e-btn-big-2.blue")
    @CacheLookup
    private WebElement depositFunds;

    @FindBy(css = "input.w-search-input.ng-valid.ng-isolate-scope.ng-touched.ng-not-empty.ng-dirty.ng-valid-parse")
    @CacheLookup
    private WebElement enterAMarketOrATraders;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(8)")
    @CacheLookup
    private WebElement etfs;

    @FindBy(css = "a[href='/dashboard/club']")
    @CacheLookup
    private WebElement etoroClub;

    @FindBy(css = "a.i-menu-link.help.ng-star-inserted")
    @CacheLookup
    private WebElement guide;

    @FindBy(css = "a[href='/learn-more']")
    @CacheLookup
    private WebElement help;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(5)")
    @CacheLookup
    private WebElement indices;

    @FindBy(css = "a[href='/discover/copyportfolios']")
    @CacheLookup
    private WebElement investInCopyportfolios;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(1) et-layout-menu div.a-menu.ng-star-inserted.closed div:nth-of-type(2) div:nth-of-type(1) a:nth-of-type(8)")
    @CacheLookup
    private WebElement inviteFriends;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(1) et-layout-menu div.a-menu.ng-star-inserted.closed div:nth-of-type(2) div:nth-of-type(1) a:nth-of-type(13)")
    @CacheLookup
    private WebElement logout;

    @FindBy(css = "a[href='/portfolio/manual-trades']")
    @CacheLookup
    private WebElement manualTradesView;

    @FindBy(css = "a.i-menu-user-username.pointer")
    @CacheLookup
    private WebElement mariusungu89;

    @FindBy(id = "toggle")
    @CacheLookup
    private WebElement menu;

    @FindBy(css = "a[href='/feed']")
    @CacheLookup
    private WebElement newsFeed;

    private final String pageLoadedText = "Start exploring investment opportunities by copying people and investing in markets or CopyPortfolios";

    private final String pageUrl = "/portfolio";

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(2)")
    @CacheLookup
    private WebElement people;

    @FindBy(css = "a.i-menu-user-avatar-ph")
    @CacheLookup
    private WebElement pl;

    @FindBy(css = "a.i-menu-link.active")
    @CacheLookup
    private WebElement portfolio;

    @FindBy(css = "a[href='/portfolio/']")
    @CacheLookup
    private WebElement portfolioOverview;

    @FindBy(css = "a[href='/settings/general']")
    @CacheLookup
    private WebElement settings;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(2) div.p-portfolio.ng-scope div:nth-of-type(1) div.inner-header div.inner-header-buttons div.filter.dropdown-menu.ng-scope div.drop-select-box a:nth-of-type(4)")
    @CacheLookup
    private WebElement stocks;

    @FindBy(css = "a[href='/discover/markets']")
    @CacheLookup
    private WebElement tradeMarkets;

    @FindBy(css = "a[href='/watchlists']")
    @CacheLookup
    private WebElement watchlist;

    @FindBy(css = "ui-layout.ng-isolate-scope div.ng-scope div.ng-scope div:nth-of-type(1) et-layout-menu div.a-menu.ng-star-inserted.closed div:nth-of-type(2) div:nth-of-type(1) a:nth-of-type(11)")
    @CacheLookup
    private WebElement withdrawFunds;

    public EToroPortfolioPage() {
    }

    public EToroPortfolioPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public EToroPortfolioPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public EToroPortfolioPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on All Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickAllLink() {
        all.click();
        return this;
    }

    /**
     * Click on Chart View Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickChartViewLink() {
        chartView.click();
        return this;
    }

    /**
     * Click on Commodities Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCommoditiesLink() {
        commodities.click();
        return this;
    }

    /**
     * Click on Complete Profile Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCompleteProfileLink() {
        completeProfile.click();
        return this;
    }

    /**
     * Click on Copy People Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCopyPeopleLink() {
        copyPeople.click();
        return this;
    }

    /**
     * Click on Copyportfolios Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCopyportfoliosLink() {
        copyportfolios.click();
        return this;
    }

    /**
     * Click on Crypto Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCryptoLink() {
        crypto.click();
        return this;
    }

    /**
     * Click on Currencies Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickCurrenciesLink() {
        currencies.click();
        return this;
    }

    /**
     * Click on Deposit Funds Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickDepositFundsLink() {
        depositFunds.click();
        return this;
    }

    /**
     * Click on Etfs Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickEtfsLink() {
        etfs.click();
        return this;
    }

    /**
     * Click on Etoro Club Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickEtoroClubLink() {
        etoroClub.click();
        return this;
    }

    /**
     * Click on Guide Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickGuideLink() {
        guide.click();
        return this;
    }

    /**
     * Click on Help Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickHelpLink() {
        help.click();
        return this;
    }

    /**
     * Click on Indices Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickIndicesLink() {
        indices.click();
        return this;
    }

    /**
     * Click on Invest In Copyportfolios Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickInvestInCopyportfoliosLink() {
        investInCopyportfolios.click();
        return this;
    }

    /**
     * Click on Invite Friends Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickInviteFriendsLink() {
        inviteFriends.click();
        return this;
    }

    /**
     * Click on Logout Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickLogoutLink() {
        logout.click();
        return this;
    }

    /**
     * Click on Manual Trades View Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickManualTradesViewLink() {
        manualTradesView.click();
        return this;
    }

    /**
     * Click on Mariusungu89 Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickMariusungu89Link() {
        mariusungu89.click();
        return this;
    }

    /**
     * Click on Menu Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickMenuLink() {
        menu.click();
        return this;
    }

    /**
     * Click on News Feed Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickNewsFeedLink() {
        newsFeed.click();
        return this;
    }

    /**
     * Click on People Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickPeopleLink() {
        people.click();
        return this;
    }

    /**
     * Click on Pl Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickPlLink() {
        pl.click();
        return this;
    }

    /**
     * Click on Portfolio Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickPortfolioLink() {
        portfolio.click();
        return this;
    }

    /**
     * Click on Portfolio Overview Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickPortfolioOverviewLink() {
        portfolioOverview.click();
        return this;
    }

    /**
     * Click on Settings Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickSettingsLink() {
        settings.click();
        return this;
    }

    /**
     * Click on Stocks Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickStocksLink() {
        stocks.click();
        return this;
    }

    /**
     * Click on Trade Markets Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickTradeMarketsLink() {
        tradeMarkets.click();
        return this;
    }

    /**
     * Click on Watchlist Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickWatchlistLink() {
        watchlist.click();
        return this;
    }

    /**
     * Click on Withdraw Funds Link.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage clickWithdrawFundsLink() {
        withdrawFunds.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage fill() {
        setEnterAMarketOrATradersTextField();
        return this;
    }

    /**
     * Set default value to Enter A Market Or A Traders Name Text field.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage setEnterAMarketOrATradersTextField() {
        return setEnterAMarketOrATradersTextField(data.get("ENTER_A_MARKET_OR_A_TRADERS"));
    }

    /**
     * Set value to Enter A Market Or A Traders Name Text field.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage setEnterAMarketOrATradersTextField(String enterAMarketOrATradersValue) {
        enterAMarketOrATraders.sendKeys(enterAMarketOrATradersValue);
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage verifyPageLoaded() {
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
     * @return the eToroPortfolioPage class instance.
     */
    public EToroPortfolioPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
