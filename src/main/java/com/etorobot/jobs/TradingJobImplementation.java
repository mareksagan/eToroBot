package com.etorobot.jobs;

import com.etorobot.Bot;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradingJobImplementation implements TradingJob {
    private Logger logger = LoggerFactory.getLogger(TradingJobImplementation.class);

    private void sellSymbol(String symbol) {

    }

    private void buySymbol(double price, String symbol) {

    }

    private void marketCloses() {
        sellAll();
    }

    private void sellAll() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
