package com.bara.JobOffers.infrastructure.offer.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

        final HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode.is5xxServerError()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while using http client. Method: " + method + " URL: " + url);
        } else if (statusCode.is4xxClientError()) {
            if (statusCode.value() == 404) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found. Method: " + method + ", URL: " + url);
            } else if (statusCode.value() == 401) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized request. Method: " + method + ", URL: " + url);
            }
            super.handleError(url, method, response);
        }
    }

}

