package com.etorobot.state;

import org.quartz.JobKey;

import java.util.HashMap;
import java.util.Map;

public enum BotState {
    INSTANCE;

    private static Map<String,Boolean> holdingSymbol = new HashMap<>();
    private static Map<String,Integer> transactionCooldownCounter = new HashMap<>();

    public static boolean isHoldingSymbol(String symbol) {
        return holdingSymbol.get(symbol);
    }

    public static void setHoldingSymbol(String symbol, boolean value) {
        BotState.holdingSymbol.put(symbol, value);
    }

    public static int getTransactionCooldownCounter(String symbol) {
        return transactionCooldownCounter.get(symbol);
    }

    public static void setTransactionCooldownCounter(String symbol, int value) {
        BotState.transactionCooldownCounter.put(symbol, value);
    }
}
