package com.etorobot;

import com.etorobot.jobs.RecommendationJobImplementation;
import com.etorobot.config.BotConfig;
import com.etorobot.state.BotState;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.TimeZone;

import static org.quartz.TriggerBuilder.newTrigger;

public class Bot {
    private static Scheduler scheduler;

    private static Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduleJobs();
        } catch (Exception e) {
            logger.error("Bot error - " + e.getMessage());
        }
    }

    private static void scheduleJobs() throws SchedulerException {
        for (String symbol : BotConfig.getSymbols()) {
            BotState.setHoldingSymbol(symbol, false);
            BotState.setTransactionCooldownCounter(symbol, 0);
            schedulePricePulling(symbol, BotConfig.getCronMarketOpen());
        }
    }

    private static void schedulePricePulling(String symbol, String cron) throws SchedulerException {
        JobDetail pricePullingJob = JobBuilder.newJob(RecommendationJobImplementation.class)
                .usingJobData("symbol", symbol)
                .storeDurably(true)
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger_" + symbol, "group_" + symbol)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cron)
                        .inTimeZone(TimeZone.getTimeZone(BotConfig.getTimezone())))
                .build();

        scheduler.scheduleJob(pricePullingJob, trigger);
    }

    private static void killScheduler() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error("Could not stop the scheduler - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }
}
