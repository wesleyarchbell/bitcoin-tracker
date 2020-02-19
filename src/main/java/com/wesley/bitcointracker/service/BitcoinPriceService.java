package com.wesley.bitcointracker.service;

import com.wesley.bitcointracker.model.BitcoinPrice;

public interface BitcoinPriceService {

    BitcoinPrice getLatestPrice();
}
