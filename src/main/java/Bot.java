import jobs.PricePullingJobImplementation;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Bot {
    private static Scheduler scheduler;

    private static Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobDetail pricePullingJob = JobBuilder.newJob(PricePullingJobImplementation.class)
                    .storeDurably(true)
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(cronSchedule("0/20 * * * * ?"))
                    .build();

            scheduler.scheduleJob(pricePullingJob, trigger);
        } catch (SchedulerException se) {
            logger.error("Scheduler error - " + se.getMessage());
        }

    }

    private void sellSymbol(String symbol) {

    }

    private void buySymbol(double price, String symbol) {

    }

    private void market_closes() {
        sellAll();
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.info("Market closed - Stocks sold");
        }
    }

    private void sellAll() {

    }
}
