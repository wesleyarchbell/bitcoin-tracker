package com.wesley.bitcointracker.service;

import java.util.List;

import com.wesley.bitcointracker.model.BitcoinPrice;

public interface BitcoinPriceService {

    List<BitcoinPrice> getHistoricalPrices();

    BitcoinPrice getLatestPrice();
}
