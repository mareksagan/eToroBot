package com.etorobot.jobs;

import com.etorobot.DTOs.NLS;
import com.etorobot.DTOs.NasdaqApiFrame;
import com.etorobot.config.BotConfig;
import com.etorobot.state.BotState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecommendationJobImplementation implements RecommendationJob {
    private Logger logger = LoggerFactory.getLogger(RecommendationJobImplementation.class);

    private double pullPrice(String symbol) throws Exception {
        double lastPrice = 0.0;
        try {
            String url = BotConfig.getApiUrl() + symbol + BotConfig.getApiParams() + BotConfig.getApiLimit();

            HttpGet getRequest = new HttpGet(url);

            CloseableHttpClient httpClient = HttpClients.createDefault();

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            // Random sleep to avoid bot detection
            Thread.sleep((long)(Math.random() * 1000));

            String responseBody = httpClient.execute(getRequest, responseHandler);

            ObjectMapper mapper = new ObjectMapper();

            NasdaqApiFrame nasdaqApiFrame = mapper.readValue(responseBody, NasdaqApiFrame.class);

            NLS[] frames = nasdaqApiFrame.getData().getRows();

            // Don't proceed when the market is closed
            if (frames.length == 0) {
                httpClient.close();
                throw new Exception("Could not get data from Nasdaq API");
            }

            File pricesFile = new File(BotConfig.getDbFilepath() + symbol.toLowerCase());
            if (!pricesFile.exists()) pricesFile.createNewFile();

            FileWriter writer = new FileWriter(pricesFile,true);

            Double weightedPriceSum = 0.0;
            Double volumeSum = 0.0;

            for (int i = 0; i < frames.length; i++) {
                // Cleaning the price string
                String parsedNumber = frames[i].getNlsPrice().replaceAll("\\$ ", "");
                String parsedShareVolume = frames[i].getNlsShareVolume().replaceAll(",", "");

                Double number = Double.parseDouble(parsedNumber);
                Double volume = Double.parseDouble(parsedShareVolume);

                weightedPriceSum =+  number * volume;
                volumeSum =+ volume;

                if (i == 0) {
                    lastPrice = Double.parseDouble(parsedNumber);
                    logger.info(symbol + " - Current price - " + lastPrice);
                }
            }

            Double weightedPrice = weightedPriceSum / volumeSum;

            // Write weighted price
            writer.write(weightedPrice + System.getProperty("line.separator"));

            writer.close();
            httpClient.close();
        } catch (IOException | InterruptedException e) {
            logger.error("Could not access prices API - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return lastPrice;
    }

    private double getLastPrice(String symbol, int nLast) throws IOException {
        Path filePath = Paths.get(BotConfig.getDbFilepath() + symbol.toLowerCase());
        long lastLineIndex = Files.lines(filePath).count();
        final String[] lastLine = { null };
        Files.lines(filePath).skip(lastLineIndex - nLast).findFirst().ifPresent(s -> lastLine[0] = s);
        Double lastPrice = Double.valueOf(lastLine[0]);
        return lastPrice;
    }

    private boolean getHoldConditions(String symbol, int multiplier) throws IOException {
        boolean result = true;
        for (int i = 1; i < multiplier; i++)
            result = result && getLastPrice(symbol, i+1) == getLastPrice(symbol, i);
        return result;
    }

    private boolean getBuyConditions(String symbol, int multiplier) throws IOException {
        boolean result = true;
        for (int i = 1; i < multiplier; i++)
            result = result && getLastPrice(symbol, i+1) >= getLastPrice(symbol, i);
        return result;
    }

    private boolean getSellConditions(String symbol, int multiplier) throws IOException {
        boolean result = true;
        for (int i = 1; i < multiplier; i++)
            result = result && getLastPrice(symbol, i+1) <= getLastPrice(symbol, i);
        return result;
    }

    private int checkTrend(String symbol, int multiplier) {
        int trendIndicator = 0;
        try {
            boolean holdConditions = getHoldConditions(symbol, multiplier);
            boolean buyConditions = getBuyConditions(symbol, multiplier  / 2);
            boolean sellConditions = getSellConditions(symbol, multiplier);
            if (holdConditions) {
                trendIndicator = 0;
            } else if (buyConditions) {
                trendIndicator = 1;
            } else if (sellConditions) {
                trendIndicator = 2;
            }
        } catch (IOException | IllegalStateException e) {
            logger.error("Could not check trend - " + symbol + " - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return trendIndicator;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            String symbol = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("symbol");

            double lastPrice = pullPrice(symbol);

            Integer multiplier = BotState.getMultiplier(symbol);
            if (multiplier == null) {
                int initialMultiplier = BotConfig.getSequenceMultiplier();
                BotState.setMultiplier(symbol, initialMultiplier);
                multiplier = initialMultiplier;
            }

            int trendIndicator = checkTrend(symbol,  multiplier);

            boolean holdingSymbol = BotState.isHoldingSymbol(symbol);
            int transactionCooldown = BotConfig.getTransactionCooldown();
            int transactionCooldownCounter = BotState.getTransactionCooldownCounter(symbol);

            boolean isTransactionReady = transactionCooldownCounter == 0;
            boolean purchaseConditions = trendIndicator == 1 && !holdingSymbol && isTransactionReady;
            boolean sellConditions = trendIndicator == 2 && holdingSymbol && isTransactionReady;

            if (transactionCooldownCounter > 0) BotState.setTransactionCooldownCounter(symbol, transactionCooldownCounter - 1);

            File tasksFile = new File(BotConfig.getTaskQueueFilepath());
            if (!tasksFile.exists()) tasksFile.createNewFile();

            FileWriter writer = new FileWriter(tasksFile,true);

            if (purchaseConditions) {
                BotState.setHoldingSymbol(symbol, true);
                BotState.setTransactionCooldownCounter(symbol, transactionCooldown);
                BotState.setLastBuyPrice(symbol, lastPrice);
                writer.write(symbol + "," + lastPrice + "," + "BUY" + System.getProperty("line.separator"));
                logger.info(symbol + " - BUY REQUEST - " + lastPrice);
            } else if (sellConditions) {
                BotState.setHoldingSymbol(symbol, false);
                BotState.setTransactionCooldownCounter(symbol, transactionCooldown);
                double lastBuyPrice = BotState.getLastPrice(symbol);
                Integer winCounter = BotState.getWinCounter(symbol);
                if (winCounter == null) {
                    BotState.setWinCounter(symbol, 0);
                    winCounter = BotState.getWinCounter(symbol);
                }
                Integer failCounter = BotState.getFailCounter(symbol);
                if (failCounter == null) {
                    BotState.setFailCounter(symbol, 0);
                    failCounter = BotState.getFailCounter(symbol);
                }
                double amountPerStock = BotConfig.getMoneyAmount() / (double) BotConfig.getSymbols().size();
                double relativeGain = (lastPrice - lastBuyPrice) * (amountPerStock / lastPrice);
                if (lastPrice > lastBuyPrice) {
                    BotState.setWinCounter(symbol, winCounter + 1);
                    winCounter = BotState.getWinCounter(symbol);
                    BotState.addSaldo(relativeGain);
                    logger.info(symbol + " - GAIN - " + relativeGain);
                } else if (lastPrice <= lastBuyPrice) {
                    BotState.setFailCounter(symbol, failCounter + 1);
                    failCounter = BotState.getFailCounter(symbol);
                    BotState.addSaldo(relativeGain);
                    logger.warn(symbol + " - LOSS - " + relativeGain);
                }
                double successRate = winCounter / (double) (winCounter + failCounter);
                logger.info(symbol + " - Current success rate - " + successRate);
                writer.write(symbol + "," + lastPrice + "," + "SELL" + System.getProperty("line.separator"));
                logger.info(symbol + " - SELL REQUEST - " + lastPrice + " - " + BotState.getSaldo());
            }

            writer.close();
        } catch (Exception e) {
            logger.error("Price pulling error - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

    }
}
