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
    private static String dbFilepath = null;
    private static String cronMarketOpen = null;
    private static Integer transactionCooldown = null;
    private static Integer dbBufferSize = null;
    private static String timezone = null;

    private static Logger logger = LoggerFactory.getLogger(BotConfig.class);

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

    public static String getCronMarketOpen() {
        if (cronMarketOpen == null) getDataFromProps();
        return cronMarketOpen;
    }

    public static Integer getDbBufferSize() {
        if (dbBufferSize == null) getDataFromProps();
        return dbBufferSize;
    }

    public static Integer getTransactionCooldown() {
        if (transactionCooldown == null) getDataFromProps();
        return transactionCooldown;
    }

    public static String getTimezone() {
        if (transactionCooldown == null) getDataFromProps();
        return timezone;
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
            dbFilepath = props.getProperty("db-filepath");
            cronMarketOpen = props.getProperty("cron-market-open");
            timezone = props.getProperty("timezone");
            dbBufferSize = Integer.parseInt(props.getProperty("db-buffer-size"));
            transactionCooldown = Integer.parseInt(props.getProperty("transaction-cooldown"));

            is.close();
        } catch (IOException e) {
            logger.error("Could not read the properties file");
        }
    }
}
