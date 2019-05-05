package com.svintsov.translator.rest;

import com.svintsov.translator.rest.model.TranslateRequest;
import com.svintsov.translator.rest.model.TranslateResponse;
import com.svintsov.translator.service.Yandex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TranslatorController {

    @Autowired
    Yandex yandex;

    @ResponseBody
    @RequestMapping(path = "/translate", method = RequestMethod.POST)
    public TranslateResponse translate(@RequestBody TranslateRequest translateRequest) {
        TranslateResponse translateResponse = new TranslateResponse();
        String translatedText = yandex.translate(translateRequest.getText(), translateRequest.getFrom(), translateRequest.getTo());
        translateResponse.setText(translatedText);
        return translateResponse;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public TranslateResponse handleBadRequest(HttpServletRequest request, Exception exception) {
        TranslateResponse translateResponse = new TranslateResponse();
        translateResponse.setError(exception.getMessage());
        return new TranslateResponse();
    }
}
