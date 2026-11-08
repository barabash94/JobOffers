package com.bara.JobOffers.domain.offer;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException {

    private final String offerId;

    public OfferNotFoundException(String offerId) {
        super("Offer with id " + offerId + " not found");
        this.offerId = offerId;
    }
}
