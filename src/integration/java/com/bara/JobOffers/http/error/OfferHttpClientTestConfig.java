package com.bara.JobOffers.http.error;

import com.bara.JobOffers.domain.offer.OfferFetchable;
import com.bara.JobOffers.infrastructure.offer.http.OfferHttpConfig;
import org.springframework.web.client.RestTemplate;

import static com.bara.JobOffers.BaseIntegrationTest.WIRE_MOCK_HOST;

public class OfferHttpClientTestConfig extends OfferHttpConfig {

    public OfferFetchable remoteOfferTestClient(int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return remoteOfferClient(restTemplate, WIRE_MOCK_HOST, port);
    }
}
