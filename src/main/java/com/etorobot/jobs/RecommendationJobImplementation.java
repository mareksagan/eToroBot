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
import java.util.Random;

public class RecommendationJobImplementation implements RecommendationJob {
    private Logger logger = LoggerFactory.getLogger(RecommendationJobImplementation.class);

    private double pullPrice(String symbol) throws Exception {
        double price = 0.0;
        try {
            String url = BotConfig.getApiUrl() + symbol + BotConfig.getApiParams();

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

            NLS firstFrame = frames[0];

            // Cleaning the price string
            String parsedNumber = firstFrame.getNlsPrice().replaceAll("\\$ ", "");
            price = Double.parseDouble(parsedNumber);
            writer.write(price + System.getProperty("line.separator"));
            logger.info(symbol + " - Current price - " + price);

            writer.close();
            httpClient.close();
        } catch (IOException | InterruptedException e) {
            logger.error("Could not access prices API - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return price;
    }

    private int checkTrend(String symbol) {
        int trendIndicator = 0;
        try {
            Path filePath = Paths.get(BotConfig.getDbFilepath() + symbol.toLowerCase());
            File dbFile = filePath.toFile();
            long lastLineIndex = Files.lines(filePath).count();
            if (lastLineIndex < 3) throw new IllegalArgumentException("Not enough data to calculate trend");
            final String[] lastLines = { null, null, null };
            Files.lines(filePath).skip(lastLineIndex - 1).findFirst().ifPresent(s -> lastLines[2] = s);
            Double lastPrice = Double.valueOf(lastLines[2]);
            Files.lines(filePath).skip(lastLineIndex - 2).findFirst().ifPresent(s -> lastLines[1] = s);
            Double secondLastPrice = Double.valueOf(lastLines[1]);
            Files.lines(filePath).skip(lastLineIndex - 3).findFirst().ifPresent(s -> lastLines[0] = s);
            Double thirdLastPrice = Double.valueOf(lastLines[0]);
            boolean isLastGreaterThanSecondLast = lastPrice > secondLastPrice;
            boolean isSecondLastGreaterThanThirdLast = secondLastPrice > thirdLastPrice;
            boolean isLastSmallerThanSecondLast = secondLastPrice < thirdLastPrice;
            boolean isSecondLastSmallerThanThirdLast = secondLastPrice < thirdLastPrice;
            int bufferSize = BotConfig.getDbBufferSize();
            if (isLastGreaterThanSecondLast && isSecondLastGreaterThanThirdLast) {
                // Making sure that no important values have been lost
                if (lastLineIndex > bufferSize) dbFile.delete();
                trendIndicator = 1;
            } else if (isLastSmallerThanSecondLast && isSecondLastSmallerThanThirdLast) {
                // Making sure that no important values have been lost
                if (lastLineIndex > bufferSize) dbFile.delete();
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

            double price = pullPrice(symbol);

            int trendIndicator = checkTrend(symbol);
            boolean holdingSymbol = BotState.isHoldingSymbol(symbol);
            int transactionCooldown = BotConfig.getTransactionCooldown();
            int transactionCooldownCounter = BotState.getTransactionCooldownCounter(symbol);

            boolean isTransactionReady = transactionCooldownCounter == 0;
            boolean purchaseConditions = trendIndicator == 2 && !holdingSymbol && isTransactionReady;
            boolean sellConditions = trendIndicator == 1 && holdingSymbol && isTransactionReady;

            if (transactionCooldownCounter > 0) BotState.setTransactionCooldownCounter(symbol, transactionCooldownCounter - 1);

            File tasksFile = new File(BotConfig.getTaskQueueFilepath());
            if (!tasksFile.exists()) tasksFile.createNewFile();

            FileWriter writer = new FileWriter(tasksFile,true);

            if (purchaseConditions) {
                BotState.setHoldingSymbol(symbol, true);
                BotState.setTransactionCooldownCounter(symbol, transactionCooldown);
                writer.write(symbol + "," + price + "," + "BUY" + System.getProperty("line.separator"));
                logger.info(symbol + " - BUY REQUEST - " + price);
            } else if (sellConditions) {
                BotState.setHoldingSymbol(symbol, false);
                BotState.setTransactionCooldownCounter(symbol, transactionCooldown);
                writer.write(symbol + "," + price + "," + "SELL" + System.getProperty("line.separator"));
                logger.info(symbol + " - SELL REQUEST - " + price);
            }

            writer.close();
        } catch (Exception e) {
            logger.error("Price pulling error - " + e.getMessage());
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

    }
}
