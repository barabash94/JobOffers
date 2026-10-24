package com.bara.JobOffers.domain.offer;

import java.util.List;

public class OfferSavingException extends RuntimeException {
    private final List<String> offerUrls;

    public OfferSavingException(String offerUrl) {
        super(String.format("Offer with offerUrl [%s] already exists", offerUrl));
        this.offerUrls = List.of(offerUrl);
    }
}
