package com.wesley.bitcointracker.service;

import java.util.ArrayList;
import java.util.List;

import com.wesley.bitcointracker.model.BitcoinPrice;

public final class MockBitcoinPriceServerImpl implements BitcoinPriceService {

    @Override
    public List<BitcoinPrice> getHistoricalPrices() {
        List<BitcoinPrice> prices = new ArrayList<>();
        prices.add(new BitcoinPrice(1233.33, "2012-01-02 02:02:02"));
        prices.add(new BitcoinPrice(900.33, "2012-01-03 02:02:02"));
        return prices;
    }

    @Override
    public BitcoinPrice getLatestPrice() {
        return new BitcoinPrice(2233.33, "2012-01-01 02:02:02");
    }
}