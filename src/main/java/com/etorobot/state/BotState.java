package com.etorobot.state;

import java.util.HashMap;
import java.util.Map;

public enum BotState {
    INSTANCE;

    private static Map<String,Boolean> holdingSymbol = new HashMap<>();
    private static Map<String,Integer> tradeCooldownCounter = new HashMap<>();
    private static Map<String,Integer> multipliers = new HashMap<>();
    private static Map<String,Double> lastBuyPrices = new HashMap<>();
    private static Map<String,Integer> failCounter = new HashMap<>();
    private static Map<String,Integer> winCounter = new HashMap<>();
    private static Long totalTPV = 0L;
    private static Long totalVolume = 0L;
    private static Double saldo = 0.0;

    public static Long getTotalTPV() {
        return totalTPV;
    }

    public static void addTPV(Long totalTPV) {
        BotState.totalTPV =+ totalTPV;
    }

    public static Long getTotalVolume() {
        return totalVolume;
    }

    public static void addVolume(Long totalVolume) {
        BotState.totalVolume =+ totalVolume;
    }

    public static Double getSaldo() {
        return saldo;
    }

    public static void addSaldo(double value) {
        saldo = BotState.saldo + value;
    }

    public static Integer getFailCounter(String symbol) {
        return failCounter.get(symbol);
    }

    public static void setFailCounter(String symbol, int value) {
        BotState.failCounter.put(symbol, value);
    }

    public static Integer getWinCounter(String symbol) {
        return winCounter.get(symbol);
    }

    public static void setWinCounter(String symbol, int value) {
        BotState.winCounter.put(symbol, value);
    }

    public static Double getLastPrice(String symbol) {
        return lastBuyPrices.get(symbol);
    }

    public static void setLastBuyPrice(String symbol, double value) {
        BotState.lastBuyPrices.put(symbol, value);
    }

    public static Integer getMultiplier(String symbol) {
        return multipliers.get(symbol);
    }

    public static void setMultiplier(String symbol, int value) {
        BotState.multipliers.put(symbol, value);
    }

    public static Boolean isHoldingSymbol(String symbol) {
        return holdingSymbol.get(symbol);
    }

    public static void setHoldingSymbol(String symbol, boolean value) {
        BotState.holdingSymbol.put(symbol, value);
    }

    public static Integer getTransactionCooldownCounter(String symbol) {
        return tradeCooldownCounter.get(symbol);
    }

    public static void setTransactionCooldownCounter(String symbol, int value) {
        BotState.tradeCooldownCounter.put(symbol, value);
    }
}
