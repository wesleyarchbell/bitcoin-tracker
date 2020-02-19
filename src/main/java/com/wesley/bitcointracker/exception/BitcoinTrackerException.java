package com.wesley.bitcointracker.exception;

public final class BitcoinTrackerException extends RuntimeException {

    public BitcoinTrackerException(String message) {
        super(message);
    }

    public BitcoinTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}