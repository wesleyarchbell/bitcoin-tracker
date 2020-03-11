package com.wesley.bitcointracker.config;

import com.wesley.bitcointracker.service.BitcoinPriceService;
import com.wesley.bitcointracker.service.MockBitcoinPriceServerImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BitcoinPriceConfig {

    @Bean
    public BitcoinPriceService bitcoinPriceService() {
        return new MockBitcoinPriceServerImpl();
    }
}