package com.wesley.bitcointracker.web;

import java.util.List;

import com.wesley.bitcointracker.model.BitcoinPrice;
import com.wesley.bitcointracker.service.BitcoinPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public final class BitcoinPriceController {

    private BitcoinPriceService bitcoinPriceService;

    @Autowired
    public BitcoinPriceController(BitcoinPriceService bitcoinPriceService) {
        this.bitcoinPriceService = bitcoinPriceService;
    }

    @CrossOrigin
    @GetMapping("/historical")
    public List<BitcoinPrice> getHistoricalPrices() {
        return bitcoinPriceService.getHistoricalPrices();
    }

    @CrossOrigin
    @GetMapping("/latest")
    public BitcoinPrice getLatestPrice() {
        return bitcoinPriceService.getLatestPrice();
    }

}