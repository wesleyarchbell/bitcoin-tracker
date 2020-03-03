package com.wesley.bitcointracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
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

public final class BitcoinPriceServiceImpl implements BitcoinPriceService {

    private static final DateTimeFormatter LAST_UPDATED_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter LAST_UPDATED_FORMATTER_2 = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final String BITCOIN_SYMBOL = "BTC";

    @Value("${historical_price_url}")
    private String historicalPriceUrl;

    @Value("${current_price_url}")
    private String currentPriceUrl;

    private final RestTemplate restTemplate;


    public BitcoinPriceServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public List<BitcoinPrice> getHistoricalPrices() {
        ResponseEntity<String> response = doGet(historicalPriceUrl);
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                String body = response.getBody();
                JsonObject jsonResponse = (JsonObject)Jsoner.deserialize(body);
                JsonObject bpi = (JsonObject)jsonResponse.get("bpi");
                return bpi.keySet().stream().sorted().collect(Collectors.toList()).stream().map(date -> {
                    BigDecimal price = (BigDecimal)bpi.get(date);
                    return new BitcoinPrice(price.doubleValue(), parseLastUpdatedHistorical(date));
                }).collect(Collectors.toList());
            } catch (JsonException e) {
                throw new BitcoinTrackerException("Unable to parse response: " + e.getMessage(), e);
            }
        }
        return Collections.emptyList();
    }

    public BitcoinPrice getLatestPrice() {
        ResponseEntity<String> response = doGet(currentPriceUrl.replaceAll("\\{symbol\\}", BITCOIN_SYMBOL));
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                String body = response.getBody();
                JsonObject jsonResponse = (JsonObject)Jsoner.deserialize(body);
                JsonObject time = (JsonObject)jsonResponse.get("time");
                String updatedISO = (String)time.get("updatedISO");
                JsonObject bpi = (JsonObject)jsonResponse.get("bpi");
                JsonObject currency = (JsonObject)bpi.get("USD");
                BigDecimal rate = (BigDecimal)currency.get("rate_float");
                return new BitcoinPrice(rate.doubleValue(), parseLastUpdated(updatedISO));
            } catch (JsonException e) {
                throw new BitcoinTrackerException("Unable to parse response: " + e.getMessage(), e);
            }
        }
        return new BitcoinPrice(0.0, null);
    }

    private ResponseEntity<String> doGet(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder
                builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
    }

    private String parseLastUpdatedHistorical(String updatedISO) {
        LocalDate parse = LocalDate.parse(updatedISO, LAST_UPDATED_FORMATTER_2);
        return parse.getYear() + "-" + parse.getMonth().getValue() + "-" + parse.getDayOfMonth() + " 00:00:00";
    }

    private String parseLastUpdated(String updatedISO) {
        LocalDateTime parse = LocalDateTime.parse(updatedISO, LAST_UPDATED_FORMATTER);
        return parse.getYear() + "-" + parse.getMonth().getValue() + "-" + parse.getDayOfMonth() + " " +
                + parse.getHour() + ":" + parse.getMinute() + ":" + parse.getSecond();
    }
}
