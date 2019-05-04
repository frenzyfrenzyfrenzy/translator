package com.svintsov.translator.exception;

import com.svintsov.translator.util.JsonUtils;

import java.util.List;

public class TranslatorValidationException extends RuntimeException {

    private List<String> validationErrors;
    private String failedObject;

    public TranslatorValidationException(List<String> validationErrors, String failedObject) {
        super(JsonUtils.silentToJsonString(validationErrors));
        this.validationErrors = validationErrors;
        this.failedObject = failedObject;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getFailedObject() {
        return failedObject;
    }

    public void setFailedObject(String failedObject) {
        this.failedObject = failedObject;
    }
}
