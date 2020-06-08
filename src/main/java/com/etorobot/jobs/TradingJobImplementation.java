package com.etorobot.jobs;

import com.etorobot.config.BotConfig;
import com.etorobot.enums.RunModeEnum;
import com.etorobot.pages.EToroLoginPage;
import org.openqa.selenium.WebDriver;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradingJobImplementation implements TradingJob {
    private Logger logger = LoggerFactory.getLogger(TradingJobImplementation.class);

    private void takeLastRequestFromTasklist() {

    }

    private void sellSymbol(String symbol) {

    }

    private void login(String login, String password) {
        //EToroLoginPage page = new EToroLoginPage(new ChromeClient(BotConfig.getSeleniumDriverFilepath()));
    }

    private void buySymbol(double price, String symbol) {

    }

    private void sellAll() {
        for (String elem : BotConfig.getSymbols())
            sellSymbol(elem);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String runModeString = jobExecutionContext.getJobDetail().getJobDataMap().get("runMode").toString();
        RunModeEnum runMode = RunModeEnum.valueOf(runModeString);

        if (runMode == RunModeEnum.STANDARD) {
            takeLastRequestFromTasklist();
        } else if (runMode == RunModeEnum.MARKET_CLOSES) {
            sellAll();
        }
    }
}
