package com.svintsov.translator.service;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.List;

public class Validator {

    private Schema translateRequestSchema;

    public Validator() {
        InputStream requestSchemaInputStream = Validator.class.getResourceAsStream("/schema/translateRequest.json");
        JSONObject rawRequestSchema = new JSONObject(new JSONTokener(requestSchemaInputStream));
        translateRequestSchema = SchemaLoader.load(rawRequestSchema);
    }

    public List<String> validateTranslateRequest(String translateRequest) {
        try {
            translateRequestSchema.validate(translateRequest);
        } catch (ValidationException validationException) {
            return validationException.getAllMessages();
        }
        return null;
    }

}
