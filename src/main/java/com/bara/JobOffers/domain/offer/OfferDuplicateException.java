package com.bara.JobOffers.domain.offer;

import java.util.List;

public class OfferDuplicateException extends RuntimeException {

    private final List<String> offerUrls;

    public OfferDuplicateException(String offerUrls) {
        super(String.format("Offer with offerUrl [%s] already exists",offerUrls));
        this.offerUrls = List.of(offerUrls);
    }
}
