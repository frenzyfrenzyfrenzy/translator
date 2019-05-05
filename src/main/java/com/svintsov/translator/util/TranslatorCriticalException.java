package com.svintsov.translator.util;

public class TranslatorCriticalException extends RuntimeException {

    public TranslatorCriticalException(String message) {
        super(message);
    }

    public TranslatorCriticalException(String message, Throwable cause) {
        super(message, cause);
    }
}
