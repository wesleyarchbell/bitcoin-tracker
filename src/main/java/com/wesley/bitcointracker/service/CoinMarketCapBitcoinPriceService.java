package com.wesley.bitcointracker.service;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesley.bitcointracker.exception.BitcoinTrackerException;
import com.wesley.bitcointracker.model.BitcoinPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public final class CoinMarketCapBitcoinPriceService implements BitcoinPriceService {

    public static final String BITCOIN_SYMBOL = "BTC";
    @Value("${bitcoin_price_url}")
    private String url;

    @Value("${coinmarketcap_api_key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CoinMarketCapBitcoinPriceService() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    public BitcoinPrice getLatestPrice() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-CMC_PRO_API_KEY", apiKey);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("symbol", BITCOIN_SYMBOL);

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        try {
            Map mapResponse = objectMapper.readValue(response.getBody(), Map.class);
            Map data = (Map)mapResponse.get("data");
            Map btc = (Map)data.get(BITCOIN_SYMBOL);
            Map quote = (Map)btc.get("quote");
            Map currency = (Map)quote.get("USD");
            Double price = (Double)currency.get("price");
            return new BitcoinPrice(price);

        } catch (JsonProcessingException e) {
            throw new BitcoinTrackerException("Unable to parse response: " + e.getMessage(), e);
        }
    }
}
