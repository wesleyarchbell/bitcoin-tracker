package com.wesley.bitcointracker.model;

public final class BitcoinPrice {

    public final double price;
    public final String lastUpdated;

    public BitcoinPrice(double price, String lastUpdated) {
        this.price = price;
        this.lastUpdated = lastUpdated;
    }
}