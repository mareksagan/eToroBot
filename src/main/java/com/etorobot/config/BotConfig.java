package com.etorobot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public enum BotConfig {

    // Ensuring singleton
    INSTANCE;

    private static List<String> symbols = null;
    private static String apiUrl = null;
    private static String apiParams = null;
    private static Integer apiLimit = null;
    private static String dbFilepath = null;
    private static String cronMarketTime = null;
    private static Integer tradeCooldown = null;
    private static String timezone = null;
    private static String taskQueueFilepath = null;
    private static String seleniumDriverFilepath = null;
    private static Double moneyAmount = null;
    private static Integer sequenceMultiplier = null;
    private static Integer vwapPrecision = null;
    private static String etoroLogin = null;
    private static String etoroPassword = null;

    private static Logger logger = LoggerFactory.getLogger(BotConfig.class);

    public static Integer getVwapPrecision() {
        return vwapPrecision;
    }

    public static String getEtoroLogin() {
        if (etoroLogin == null) getDataFromProps();
        return etoroLogin;
    }

    public static String getEtoroPassword() {
        if (etoroPassword == null) getDataFromProps();
        return etoroPassword;
    }

    public static Double getMoneyAmount() {
        if (moneyAmount == null) getDataFromProps();
        return moneyAmount;
    }

    public static Integer getSequenceMultiplier() {
        if (sequenceMultiplier == null) getDataFromProps();
        return sequenceMultiplier;
    }

    public static Integer getApiLimit() {
        if (apiLimit == null) getDataFromProps();
        return apiLimit;
    }

    public static List<String> getSymbols() {
        if (symbols == null) getDataFromProps();
        return symbols;
    }

    public static String getApiUrl() {
        if (apiUrl == null) getDataFromProps();
        return apiUrl;
    }

    public static String getApiParams() {
        if (apiParams == null) getDataFromProps();
        return apiParams;
    }

    public static String getDbFilepath() {
        if (dbFilepath == null) getDataFromProps();
        return dbFilepath;
    }

    public static String getCronMarketTime() {
        if (cronMarketTime == null) getDataFromProps();
        return cronMarketTime;
    }

    public static Integer getTradeCooldown() {
        if (tradeCooldown == null) getDataFromProps();
        return tradeCooldown;
    }

    public static String getTimezone() {
        if (tradeCooldown == null) getDataFromProps();
        return timezone;
    }

    public static String getTaskQueueFilepath() {
        if (taskQueueFilepath == null) getDataFromProps();
        return taskQueueFilepath;
    }

    public static String getSeleniumDriverFilepath() {
        if (seleniumDriverFilepath == null) getDataFromProps();
        return seleniumDriverFilepath;
    }

    public static void getDataFromProps() {
        InputStream is = null;

        try {
            Properties props = new Properties();
            ClassLoader cl = BotConfig.class.getClassLoader();
            is = cl.getResourceAsStream("bot.properties");

            props.load(is);

            String symbolsInputString = props.getProperty("symbols");
            symbols = new ArrayList<>();
            for (String elem : symbolsInputString.split(",")) symbols.add(elem.trim());
            apiUrl = props.getProperty("api");
            apiParams = props.getProperty("api-params");
            apiLimit = Integer.parseInt(props.getProperty("api-limit"));
            dbFilepath = props.getProperty("db-filepath");
            cronMarketTime = props.getProperty("cron-market-time");
            timezone = props.getProperty("timezone");
            tradeCooldown = Integer.parseInt(props.getProperty("trade-cooldown"));
            taskQueueFilepath = props.getProperty("task-queue-filepath");
            seleniumDriverFilepath = props.getProperty("selenium-driver-filepath");
            sequenceMultiplier = Integer.parseInt(props.getProperty("sequence-multiplier"));
            vwapPrecision = Integer.parseInt(props.getProperty("vwap-precision"));
            moneyAmount = Double.parseDouble(props.getProperty("money-amount"));
            etoroLogin = props.getProperty("etoro-login");
            etoroPassword = props.getProperty("etoro-password");

            is.close();
        } catch (IOException e) {
            logger.error("Could not read the properties file");
        }
    }
}
