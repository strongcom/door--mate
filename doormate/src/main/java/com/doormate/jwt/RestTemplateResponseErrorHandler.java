package com.doormate.jwt;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private List<HttpMessageConverter<?>> messageConverters = Arrays.asList(new MappingJackson2HttpMessageConverter());

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            super.handleError(response);
        } catch (HttpStatusCodeException e) {
            System.out.println("Error response body is " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
